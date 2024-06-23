/*
 * Copyright 2020 Google LLC. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.modarb.android.posedetection.classification;

import static com.modarb.android.posedetection.classification.PoseEmbedding.getPoseEmbedding;
import static com.modarb.android.posedetection.classification.Utils.maxAbs;
import static com.modarb.android.posedetection.classification.Utils.multiply;
import static com.modarb.android.posedetection.classification.Utils.multiplyAll;
import static com.modarb.android.posedetection.classification.Utils.subtract;
import static com.modarb.android.posedetection.classification.Utils.sumAbs;
import static java.lang.Math.max;
import static java.lang.Math.min;

import android.util.Pair;

import com.google.mlkit.vision.common.PointF3D;
import com.google.mlkit.vision.pose.Pose;
import com.google.mlkit.vision.pose.PoseLandmark;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;


public class PoseClassifier {
    private static final String TAG = "PoseClassifier";
    private static final int MAX_DISTANCE_TOP_K = 30;
    private static final int MEAN_DISTANCE_TOP_K = 10;
    private static final PointF3D AXES_WEIGHTS = PointF3D.from(1, 1, 0.2f);

    private final List<PoseSample> poseSamples;
    private final int maxDistanceTopK;
    private final int meanDistanceTopK;
    private final PointF3D axesWeights;

    public PoseClassifier(List<PoseSample> poseSamples) {
        this(poseSamples, MAX_DISTANCE_TOP_K, MEAN_DISTANCE_TOP_K, AXES_WEIGHTS);
    }

    public PoseClassifier(List<PoseSample> poseSamples, int maxDistanceTopK, int meanDistanceTopK, PointF3D axesWeights) {
        this.poseSamples = poseSamples;
        this.maxDistanceTopK = maxDistanceTopK;
        this.meanDistanceTopK = meanDistanceTopK;
        this.axesWeights = axesWeights;
    }

    private static List<PointF3D> extractPoseLandmarks(Pose pose) {
        List<PointF3D> landmarks = new ArrayList<>();
        for (PoseLandmark poseLandmark : pose.getAllPoseLandmarks()) {
            landmarks.add(poseLandmark.getPosition3D());
        }
        return landmarks;
    }


    public int confidenceRange() {
        return min(maxDistanceTopK, meanDistanceTopK);
    }

    public ClassificationResult classify(Pose pose) {
        return classify(extractPoseLandmarks(pose));
    }

    public ClassificationResult classify(List<PointF3D> landmarks) {
        ClassificationResult result = new ClassificationResult();
        // Return early if no landmarks detected.
        if (landmarks.isEmpty()) {
            return result;
        }

        List<PointF3D> flippedLandmarks = new ArrayList<>(landmarks);
        multiplyAll(flippedLandmarks, PointF3D.from(-1, 1, 1));

        List<PointF3D> embedding = getPoseEmbedding(landmarks);
        List<PointF3D> flippedEmbedding = getPoseEmbedding(flippedLandmarks);


        PriorityQueue<Pair<PoseSample, Float>> maxDistances = new PriorityQueue<>(maxDistanceTopK, (o1, o2) -> -Float.compare(o1.second, o2.second));
        for (PoseSample poseSample : poseSamples) {
            List<PointF3D> sampleEmbedding = poseSample.getEmbedding();

            float originalMax = 0;
            float flippedMax = 0;
            for (int i = 0; i < embedding.size(); i++) {
                originalMax = max(originalMax, maxAbs(multiply(subtract(embedding.get(i), sampleEmbedding.get(i)), axesWeights)));
                flippedMax = max(flippedMax, maxAbs(multiply(subtract(flippedEmbedding.get(i), sampleEmbedding.get(i)), axesWeights)));
            }
            maxDistances.add(new Pair<>(poseSample, min(originalMax, flippedMax)));

            if (maxDistances.size() > maxDistanceTopK) {
                maxDistances.poll();
            }
        }

        PriorityQueue<Pair<PoseSample, Float>> meanDistances = new PriorityQueue<>(meanDistanceTopK, (o1, o2) -> -Float.compare(o1.second, o2.second));
        for (Pair<PoseSample, Float> sampleDistances : maxDistances) {
            PoseSample poseSample = sampleDistances.first;
            List<PointF3D> sampleEmbedding = poseSample.getEmbedding();

            float originalSum = 0;
            float flippedSum = 0;
            for (int i = 0; i < embedding.size(); i++) {
                originalSum += sumAbs(multiply(subtract(embedding.get(i), sampleEmbedding.get(i)), axesWeights));
                flippedSum += sumAbs(multiply(subtract(flippedEmbedding.get(i), sampleEmbedding.get(i)), axesWeights));
            }
            float meanDistance = min(originalSum, flippedSum) / (embedding.size() * 2);
            meanDistances.add(new Pair<>(poseSample, meanDistance));
            if (meanDistances.size() > meanDistanceTopK) {
                meanDistances.poll();
            }
        }

        for (Pair<PoseSample, Float> sampleDistances : meanDistances) {
            String className = sampleDistances.first.getClassName();
            result.incrementClassConfidence(className);
        }

        return result;
    }
}
