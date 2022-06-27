package com.supersweet.luck.dialog

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import butterknife.BindView
import com.blankj.utilcode.util.ScreenUtils
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.supersweet.luck.R
import com.supersweet.luck.widget.AppBar

/**
 * @ClassName:  BioRhythmGuideDialog
 * @Description:生物节律引导弹窗
 * @Author: vi1zen
 * @CreateDate: 2022/3/8 13:36
 */
class BioRhythmGuideDialog(context: Context) : BottomSheetDialog(context) {

    @BindView(R.id.recyclerview)
    var recyclerview: RecyclerView? = null
    @BindView(R.id.tv_text)
    var tv_text: TextView? = null
    @BindView(R.id.banner)
    var viewPager: ViewPager? = null
    @BindView(R.id.point_group)
    var pointGroup: LinearLayout? = null


    var imageList = mutableListOf<ImageView>()
    private val imageIds = intArrayOf(
        R.mipmap.dialog_google_pay_one,
        R.mipmap.dialog_google_pay_two,
        R.mipmap.dialog_google_pay_three,
        R.mipmap.dialog_google_pay_four,
        R.mipmap.dialog_google_pay_five,
        R.mipmap.dialog_google_pay_six
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_every_month_pay)
    }

    override fun onStart() {
        super.onStart()
        setBottomSheetBehavior()

        window?.attributes?.apply {
            gravity = Gravity.BOTTOM
            width = ScreenUtils.getScreenWidth()
            height = (ScreenUtils.getAppScreenHeight() * 0.9f).toInt()
        }

        for (i in imageIds.indices) {
            // 初始化图片资源
            val image = ImageView(context)
            image.setBackgroundResource(imageIds[i])
            imageList .add(image)

            // 添加指示点
            val point = ImageView(context)
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.rightMargin = 20
            point.layoutParams = params
            point.setImageDrawable(context.getResources().getDrawable(R.drawable.point_bg))
            if (i == 0) {
                point.isEnabled = true
            } else {
                point.isEnabled = false
            }
            pointGroup?.addView(point)
        }
    }

    private fun setBottomSheetBehavior() {
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
        behavior.skipCollapsed = true
        behavior.isFitToContents = true
        findViewById<View>(R.id.design_bottom_sheet)?.run {
            setBackgroundColor(Color.TRANSPARENT)
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
    }
}