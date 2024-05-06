package com.modarb.android.ui.onboarding.activities

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import com.modarb.android.databinding.ActivitySplashBinding
import com.modarb.android.ui.home.HomeActivity
import com.modarb.android.ui.onboarding.utils.UserPref.UserPrefUtil


class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val shrinkAnimator = ObjectAnimator.ofFloat(binding.image, "scaleX", 1f, 0.0f)
        shrinkAnimator.duration = 960
        shrinkAnimator.interpolator = AccelerateDecelerateInterpolator()
        shrinkAnimator.start()


        handler.postDelayed({
            startUpDownAnimation()
        }, 800)


    }

    private fun startUpDownAnimation() {
        binding.image2.visibility = View.VISIBLE
        val animator = ObjectAnimator.ofFloat(binding.image2, "translationY", 200f, 0f)
        animator.duration = 1000
        animator.interpolator = AccelerateDecelerateInterpolator()
        animator.repeatCount = 1
        animator.repeatMode = ObjectAnimator.REVERSE

        // Add AnimatorListener
        animator.addListener(object : Animator.AnimatorListener {

            override fun onAnimationStart(animation: Animator) {
            }

            override fun onAnimationEnd(animation: Animator) {

                handler.postDelayed({
                    if (!UserPrefUtil.isUserLoggedIn(this@SplashActivity)) {
                        startActivity(
                            Intent(
                                this@SplashActivity, WelcomeScreenActivity::class.java
                            )
                        )
                    } else {
                        startActivity(
                            Intent(
                                this@SplashActivity, HomeActivity::class.java
                            )
                        )
                    }
                    finish()
                }, 200)

            }

            override fun onAnimationCancel(animation: Animator) {
            }

            override fun onAnimationRepeat(animation: Animator) {
            }
        })

        animator.start()
    }

}