package com.app.evergrow.Common

import android.content.Context
import android.widget.ImageView
import com.squareup.picasso.Picasso
import ss.com.bannerslider.ImageLoadingService

class PicassoImageLoadingService(var context: Context) : ImageLoadingService {

    var contexts:Context
    init {
        this.contexts=context
    }


    override fun loadImage(url: String?, imageView: ImageView?) {
        Picasso.with(context).load(url).into(imageView)
    }

    override fun loadImage(resource: Int, imageView: ImageView?) {
        Picasso.with(context).load(resource).into(imageView)
    }

    override fun loadImage(
        url: String?,
        placeHolder: Int,
        errorDrawable: Int,
        imageView: ImageView?
    ) {
        Picasso.with(context).load(url).placeholder(placeHolder).error(errorDrawable).into(imageView)
    }

}