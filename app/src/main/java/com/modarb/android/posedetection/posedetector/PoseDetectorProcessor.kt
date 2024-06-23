package com.modarb.android.posedetection.posedetector

import android.content.Context
import android.os.Build
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.android.gms.tasks.Task
import com.google.android.odml.image.MlImage
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.demo.kotlin.VisionProcessorBase
import com.google.mlkit.vision.pose.Pose
import com.google.mlkit.vision.pose.PoseDetection
import com.google.mlkit.vision.pose.PoseDetector
import com.google.mlkit.vision.pose.PoseDetectorOptionsBase
import com.google.mlkit.vision.pose.PoseLandmark
import com.modarb.android.posedetection.GraphicOverlay
import com.modarb.android.posedetection.classification.PoseClassifierProcessor
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.util.Locale
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import kotlin.coroutines.CoroutineContext
import kotlin.math.atan2

class PoseDetectorProcessor(
    private val context: Context,
    options: PoseDetectorOptionsBase,
    private val showInFrameLikelihood: Boolean,
    private val visualizeZ: Boolean,
    private val rescaleZForVisualization: Boolean,
    private val runClassification: Boolean,
    private val isStreamMode: Boolean
) : VisionProcessorBase<PoseDetectorProcessor.PoseWithClassification>(context), CoroutineScope {

    private val detector: PoseDetector
    private val classificationExecutor: Executor

    private var poseClassifierProcessor: PoseClassifierProcessor? = null

    class PoseWithClassification(val pose: Pose, val classificationResult: List<String>)

    init {
        detector = PoseDetection.getClient(options)
        classificationExecutor = Executors.newSingleThreadExecutor()
    }

    override fun stop() {
        super.stop()
        detector.close()
        stopPushUpFormCheck()
    }

    override fun detectInImage(image: InputImage): Task<PoseWithClassification> {
        return detector.process(image).continueWith(classificationExecutor) { task ->
            val pose = task.getResult()
            var classificationResult: List<String> = ArrayList()
            if (runClassification) {
                if (poseClassifierProcessor == null) {
                    poseClassifierProcessor = PoseClassifierProcessor(context, isStreamMode)
                }
                classificationResult = poseClassifierProcessor!!.getPoseResult(pose)
            }
            PoseWithClassification(pose, classificationResult)
        }
    }


    override fun detectInImage(image: MlImage): Task<PoseWithClassification> {
        return detector.process(image).continueWith(
            classificationExecutor
        ) { task ->
            val pose = task.getResult()
            var classificationResult: List<String> = ArrayList()
            if (runClassification) {
                if (poseClassifierProcessor == null) {
                    poseClassifierProcessor = PoseClassifierProcessor(context, isStreamMode)
                }
                classificationResult = poseClassifierProcessor!!.getPoseResult(pose)
            }
            PoseWithClassification(pose, classificationResult)
        }
    }

    private var pushUpCheckJob: Job? = null

    override fun onSuccess(
        results: PoseWithClassification, graphicOverlay: GraphicOverlay
    ) {
        graphicOverlay.add(
            PoseGraphic(
                graphicOverlay,
                results.pose,
                showInFrameLikelihood,
                visualizeZ,
                rescaleZForVisualization,
                results.classificationResult
            )
        )
        Log.d("Result", results.classificationResult.toString())

        val containsPushup = results.classificationResult.any { "pushup" in it }

        if (containsPushup) {
            startPushUpFormCheck(results)
        } else {
            stopPushUpFormCheck()
        }
    }

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        println("Coroutine exception occurred: ${throwable.message}")
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + coroutineExceptionHandler

    private fun startPushUpFormCheck(results: PoseWithClassification) {
        pushUpCheckJob?.cancel()
        pushUpCheckJob = launch {
            while (isActive) {
                results.pose.allPoseLandmarks.let {
                    checkPushUpForm(it)
                }
                delay(1000)
            }
        }
    }

    private fun stopPushUpFormCheck() {
        pushUpCheckJob?.cancel()
    }

    override fun onFailure(e: Exception) {
        Log.e(TAG, "Pose detection failed!", e)
    }

    override fun isMlImageEnabled(context: Context?): Boolean {
        return true
    }


    private fun debugPose(pose: Pose) {
        val landmarkTypes = listOf(
            PoseLandmark.NOSE,
            PoseLandmark.LEFT_EYE_INNER,
            PoseLandmark.LEFT_EYE,
            PoseLandmark.LEFT_EYE_OUTER,
            PoseLandmark.RIGHT_EYE_INNER,
            PoseLandmark.RIGHT_EYE,
            PoseLandmark.RIGHT_EYE_OUTER,
            PoseLandmark.LEFT_EAR,
            PoseLandmark.RIGHT_EAR,
            PoseLandmark.LEFT_SHOULDER,
            PoseLandmark.RIGHT_SHOULDER,
            PoseLandmark.LEFT_ELBOW,
            PoseLandmark.RIGHT_ELBOW,
            PoseLandmark.LEFT_WRIST,
            PoseLandmark.RIGHT_WRIST,
            PoseLandmark.LEFT_HIP,
            PoseLandmark.RIGHT_HIP,
            PoseLandmark.LEFT_KNEE,
            PoseLandmark.RIGHT_KNEE,
            PoseLandmark.LEFT_ANKLE,
            PoseLandmark.RIGHT_ANKLE
        )

        for (landmarkType in landmarkTypes) {
            val landmark = pose.getPoseLandmark(landmarkType)
            if (landmark != null) {
                Log.d(
                    TAG,
                    "Landmark: ${landmark.landmarkType}, Position: ${landmark.position3D.x}, ${landmark.position3D.y}, ${landmark.position3D.z}"
                )
            } else {
                Log.d(TAG, "Landmark: $landmarkType, Not detected")
            }
        }

    }

    private fun getAngle(
        firstPoint: PoseLandmark, midPoint: PoseLandmark, lastPoint: PoseLandmark
    ): Double {
        var result = Math.toDegrees(
            (atan2(
                lastPoint.position.y - midPoint.position.y,
                lastPoint.position.x - midPoint.position.x
            ) - atan2(
                firstPoint.position.y - midPoint.position.y,
                firstPoint.position.x - midPoint.position.x
            )).toDouble()
        )
        result = Math.abs(result)
        if (result > 180) {
            result = 360.0 - result
        }
        return result
    }


    private var textToSpeech: TextToSpeech? = null
    private var isSpeaking: Boolean = false

    init {
        textToSpeech = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                textToSpeech?.language = Locale.US
                textToSpeech?.setOnUtteranceCompletedListener {
                    isSpeaking = false
                }
            } else {

            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun isBodyStraight(landmarks: List<PoseLandmark>): Pair<Boolean, String> {
        val leftShoulder =
            landmarks.find { it.landmarkType == PoseLandmark.LEFT_SHOULDER } ?: return Pair(
                false, "Left shoulder not found"
            )
        val rightShoulder =
            landmarks.find { it.landmarkType == PoseLandmark.RIGHT_SHOULDER } ?: return Pair(
                false, "Right shoulder not found"
            )
        val leftHip = landmarks.find { it.landmarkType == PoseLandmark.LEFT_HIP } ?: return Pair(
            false, "Left hip not found"
        )
        val rightHip = landmarks.find { it.landmarkType == PoseLandmark.RIGHT_HIP } ?: return Pair(
            false, "Right hip not found"
        )
        val leftKnee = landmarks.find { it.landmarkType == PoseLandmark.LEFT_KNEE } ?: return Pair(
            false, "Left knee not found"
        )
        val rightKnee =
            landmarks.find { it.landmarkType == PoseLandmark.RIGHT_KNEE } ?: return Pair(
                false, "Right knee not found"
            )

        val leftBodyAngle = getAngle(leftShoulder, leftHip, leftKnee)
        val rightBodyAngle = getAngle(rightShoulder, rightHip, rightKnee)

        return if (leftBodyAngle > 160 && rightBodyAngle > 160) {
            Pair(true, "Body is straight")
        } else {
            speak("Straight your body.")
            Pair(
                false,
                "Body is not straight. Left body angle: $leftBodyAngle, right body angle: $rightBodyAngle"
            )
        }
    }

    private fun speak(text: String) {
        if (!isSpeaking) {
            isSpeaking = true
            val params = HashMap<String, String>()
            params[TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID] = "uniqueId"
            textToSpeech?.speak(text, TextToSpeech.QUEUE_FLUSH, params)
        }
    }

    fun shutdown() {
        textToSpeech?.shutdown()
    }


    private fun areShouldersAboveWrists(landmarks: List<PoseLandmark>): Pair<Boolean, String> {
        val leftShoulder =
            landmarks.find { it.landmarkType == PoseLandmark.LEFT_SHOULDER } ?: return Pair(
                false, "Left shoulder not found"
            )
        val rightShoulder =
            landmarks.find { it.landmarkType == PoseLandmark.RIGHT_SHOULDER } ?: return Pair(
                false, "Right shoulder not found"
            )
        val leftWrist =
            landmarks.find { it.landmarkType == PoseLandmark.LEFT_WRIST } ?: return Pair(
                false, "Left wrist not found"
            )
        val rightWrist =
            landmarks.find { it.landmarkType == PoseLandmark.RIGHT_WRIST } ?: return Pair(
                false, "Right wrist not found"
            )

        return if (leftShoulder.position.y < leftWrist.position.y && rightShoulder.position.y < rightWrist.position.y) {
            Pair(true, "Shoulders are above wrists")
        } else {
            speak("Shoulders are not above wrists.")
            Pair(
                false,
                "Shoulders are not above wrists. Left shoulder y: ${leftShoulder.position.y}, left wrist y: ${leftWrist.position.y}, right shoulder y: ${rightShoulder.position.y}, right wrist y: ${rightWrist.position.y}"
            )
        }
    }

    private fun areElbowsBendingCorrectly(landmarks: List<PoseLandmark>): Pair<Boolean, String> {
        val leftElbow =
            landmarks.find { it.landmarkType == PoseLandmark.LEFT_ELBOW } ?: return Pair(
                false, "Left elbow not found"
            )
        val rightElbow =
            landmarks.find { it.landmarkType == PoseLandmark.RIGHT_ELBOW } ?: return Pair(
                false, "Right elbow not found"
            )
        val leftShoulder =
            landmarks.find { it.landmarkType == PoseLandmark.LEFT_SHOULDER } ?: return Pair(
                false, "Left shoulder not found"
            )
        val rightShoulder =
            landmarks.find { it.landmarkType == PoseLandmark.RIGHT_SHOULDER } ?: return Pair(
                false, "Right shoulder not found"
            )
        val leftWrist =
            landmarks.find { it.landmarkType == PoseLandmark.LEFT_WRIST } ?: return Pair(
                false, "Left wrist not found"
            )
        val rightWrist =
            landmarks.find { it.landmarkType == PoseLandmark.RIGHT_WRIST } ?: return Pair(
                false, "Right wrist not found"
            )

        val leftElbowAngle = getAngle(leftShoulder, leftElbow, leftWrist)
        val rightElbowAngle = getAngle(rightShoulder, rightElbow, rightWrist)

        return if (leftElbowAngle < 170 && rightElbowAngle < 170) {
            Pair(true, "Elbows are bending correctly")
        } else {
            //speak("Elbows are not bending correctly.")
            Pair(
                false,
                "Elbows are not bending correctly. Left elbow angle: $leftElbowAngle, right elbow angle: $rightElbowAngle"
            )
        }
    }

    private fun checkPushUpForm(landmarks: List<PoseLandmark>) {
        val (_, shouldersAboveWristsMsg) = areShouldersAboveWrists(landmarks)
        val (_, bodyStraightMsg) = isBodyStraight(landmarks)
        val (_, elbowsBendingCorrectlyMsg) = areElbowsBendingCorrectly(
            landmarks
        )

        println(shouldersAboveWristsMsg)
        println(bodyStraightMsg)
        println(elbowsBendingCorrectlyMsg)
    }


    companion object {
        private val TAG = "PoseDetectorProcessor"
    }
}
