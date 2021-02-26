package com.app.evergrow.Adapter

import com.app.evergrow.R
import ss.com.bannerslider.adapters.SliderAdapter
import ss.com.bannerslider.viewholder.ImageSlideViewHolder

class HomeBannerAdapter() : SliderAdapter() {

    override fun getItemCount(): Int {
        return 4
    }

    override fun onBindImageSlide(position: Int, viewHolder: ImageSlideViewHolder?) {
        when (position) {
            0 -> viewHolder?.bindImageSlide(R.drawable.bannerone)
            1 -> viewHolder?.bindImageSlide(R.drawable.bannertwo)
            2 -> viewHolder?.bindImageSlide(R.drawable.bannerfour)
            3 -> viewHolder?.bindImageSlide(R.drawable.bannerthree)
        }
    }

}