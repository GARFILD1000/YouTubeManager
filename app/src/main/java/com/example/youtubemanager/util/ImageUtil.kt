package com.example.youtubemanager.util
import android.content.res.Resources
import android.graphics.drawable.BitmapDrawable
import android.widget.ImageView
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import com.squareup.picasso.Callback as PicassoCallback
import com.squareup.picasso.RequestCreator

fun RequestCreator.intoAsCircle(imageView: ImageView, resources: Resources){
    this.into(imageView, object : PicassoCallback {
        override fun onError(e: Exception?) {}
        override fun onSuccess() {
            val imageBitmap = (imageView.getDrawable() as BitmapDrawable).bitmap
            val imageDrawable = RoundedBitmapDrawableFactory.create(resources, imageBitmap)
            imageDrawable.isCircular = true
            imageDrawable.cornerRadius =
                Math.max(imageBitmap.width, imageBitmap.height) / 2.0f
            imageView.setImageDrawable(imageDrawable)
        }
    })
}

fun RequestCreator.intoWithRounding(imageView: ImageView, resources: Resources, radius: Float){
    this.into(imageView, object : PicassoCallback {
        override fun onError(e: Exception?) {}
        override fun onSuccess() {
            val imageBitmap = (imageView.getDrawable() as BitmapDrawable).bitmap
            val imageDrawable = RoundedBitmapDrawableFactory.create(resources, imageBitmap)
            imageDrawable.isCircular = true
            imageDrawable.cornerRadius = radius
            imageView.setImageDrawable(imageDrawable)
        }
    })
}

class ImageUtil {

}