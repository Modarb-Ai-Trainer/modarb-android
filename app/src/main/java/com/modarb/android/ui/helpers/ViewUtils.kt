package com.modarb.android.ui.helpers

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.modarb.android.R

object ViewUtils {


    fun loadImage(context: Context, imageUrl: String, imageView: ImageView) {
        Glide.with(context).load(imageUrl).placeholder(R.drawable.baseline_broken_image_24)
            .centerCrop().into(imageView)
    }


}