package com.example.pruebatecnica.extensions

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

fun ImageView.loadCircularImage(
    context: Context,
    url: String?,
    errorDrawable: Drawable?
) {
    Glide.with(context)
        .load(url)
        .apply(RequestOptions.circleCropTransform())
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .error(errorDrawable)
        .into(this)
}