package com.app.evergrow.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter


class ScreenSlidePagerAdapter internal constructor(fm: FragmentManager, behavior: Int) : FragmentStatePagerAdapter(
    fm,behavior
) {

    private val mFragmentList: ArrayList<Fragment> = ArrayList()
    private val mFragmentTitleList: ArrayList<String> = ArrayList()

    override fun getCount(): Int {
        return mFragmentList.size
    }
    fun addFragment(fragment: Fragment, title: String) {
        mFragmentList.add(fragment)
        mFragmentTitleList.add(title)
    }
    override fun getItem(position: Int): Fragment {
        return mFragmentList.get(position)
    }
}