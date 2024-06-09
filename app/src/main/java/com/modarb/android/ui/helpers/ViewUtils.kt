package com.modarb.android.ui.helpers

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

object ViewUtils {


    fun loadImage(context: Context, imageUrl: String, imageView: ImageView) {
        Glide.with(context).asBitmap().load(imageUrl).into(imageView)
    }


}