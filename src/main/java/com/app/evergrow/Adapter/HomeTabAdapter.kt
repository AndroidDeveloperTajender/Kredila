package com.app.evergrow.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.app.evergrow.Fragment.*

class HomeTabAdapter(fm: FragmentManager,behavior:Int)  : FragmentPagerAdapter(fm,behavior) {
    override fun getCount(): Int {
        return 6
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                HomeFragment()
            }
            1 -> MyAccountFragment()
            2 -> TrackFragment()
            3 -> SupportFragment()
            4 -> NotificaitonFragment()
            5 -> FeedBackFragment()
            else -> {
                return FeedBackFragment()
            }
        }
    }
    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "Home"
            1 -> "My Account"
            2 -> "Track"
            3 -> "Support"
            4 -> "Notification"
            5 -> "FeedBack"
            else -> {
                return "Feedback"
            }
        }
    }
}