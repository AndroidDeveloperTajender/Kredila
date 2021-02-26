package com.app.evergrow


import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import java.time.temporal.TemporalAdjusters.next


class IntroSliderActivity : AppCompatActivity() {
    private var btnNext: Button? = null
    private var viewPager: ViewPager? = null
    private var myViewPagerAdapter: MyViewPagerAdapter? = null
    private var dotsLayout: LinearLayout? = null
    private var dots: Array<TextView?> = emptyArray()
    private var layouts: IntArray? = null
   // var myvpAdapter: MyViewPagerAdapter? = null
    lateinit var screens: IntArray

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro_slider)

        viewPager = findViewById(R.id.view_pager)
        dotsLayout = findViewById(R.id.layoutDots)
        btnNext = findViewById(R.id.btn_next)
       // myvpAdapter = MyViewPagerAdapter()
        //vp!!.adapter = myvpAdapter

     //   vp!!.addOnPageChangeListener(viewPagerPageChangeListener);
        screens = intArrayOf(
          //  R.layout.intro_screen1,
          //  R.layout.intro_screen2,
          //  R.layout.intro_screen3
        )
        //ColoredBars(0)

        changeStatusBarColor()
        setDots()
        setAdapter()


        btnNext!!.setOnClickListener {
            val current = getItem(+1)
            if (current < layouts!!.size) {
                viewPager!!.currentItem = current
            } else {
               startActivity(Intent(this,RegisterActivity::class.java))
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private var viewPagerPageChangeListener: ViewPager.OnPageChangeListener = object : ViewPager.OnPageChangeListener {
        override fun onPageSelected(position: Int) {
         //   addBottomDots(position)
            if (position == layouts!!.size - 1) {
                btnNext!!.text ="APPLY NOW"
               // btnNext!!.visibility=View.GONE
               // btnSkip!!.visibility = View.GONE
            } else {
                btnNext!!.visibility=View.VISIBLE
                btnNext!!.text = "Next"
                    // btnSkip!!.visibility = View.VISIBLE
            }
        }

        override fun onPageScrolled(arg0: Int, arg1: Float, arg2: Int) {}

        override fun onPageScrollStateChanged(arg0: Int) {}
    }

    private fun addBottomDots(currentPage: Int) {
        dots = arrayOfNulls(layouts!!.size)
        val colorsActive = resources.getIntArray(R.array.array_dot_active)
        val colorsInactive = resources.getIntArray(R.array.array_dot_inactive)
        dotsLayout!!.removeAllViews()
        for (i in dots.indices) {
            dots[i] = TextView(this)
            dots[i]!!.text = Html.fromHtml("&#8226;")
            dots[i]!!.textSize = 35f
            dots[i]!!.setTextColor(colorsInactive[currentPage])
            dotsLayout!!.addView(dots[i])
        }
        if (dots.isNotEmpty())
            dots[currentPage]!!.setTextColor(colorsActive[currentPage])
    }
    private fun getItem(i: Int): Int {
        return viewPager!!.currentItem + i
    }
    private fun setDots() {
        layouts =
            intArrayOf(R.layout.fragment_on_borading_intro1, R.layout.fragment_on_borading_intro2,
            R.layout.fragment_on_borading_intro3)
      //  addBottomDots(0)
    }
    private fun setAdapter() {
        myViewPagerAdapter = MyViewPagerAdapter()
        viewPager!!.adapter = myViewPagerAdapter
        viewPager!!.addOnPageChangeListener(viewPagerPageChangeListener)
    }
    private fun changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        }
    }

    inner class MyViewPagerAdapter : PagerAdapter() {
        private var layoutInflater: LayoutInflater? = null

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            layoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view = layoutInflater!!.inflate(layouts!![position], container, false)
            container.addView(view)
            return view
        }

        override fun getCount(): Int {
            return layouts!!.size
        }

        override fun isViewFromObject(view: View, obj: Any): Boolean {
            return view === obj
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            val view = `object` as View
            container.removeView(view)
        }
    }
}

