package com.kanneki.moneybook.binding

import android.annotation.SuppressLint
import android.app.Activity
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewTreeObserver
import android.view.animation.AccelerateInterpolator
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.databinding.BindingAdapter
import com.kanneki.moneybook.R
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.max

@BindingAdapter("revealEffectReveal")
fun BindStartActivity(view: View, isTrue: Boolean){

    view.visibility = View.INVISIBLE

    if (view.viewTreeObserver.isAlive){
        view.viewTreeObserver.addOnGlobalLayoutListener(
                object : ViewTreeObserver.OnGlobalLayoutListener {

                    override fun onGlobalLayout() {
                        startReveal(view)
                        view.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    }
                })
    }

}

private fun startReveal(view: View){
    val startRadius = max(view.width, view.height).toFloat()
    val endRadius = 0.0f
    val circularReveal = ViewAnimationUtils.createCircularReveal(
            view,
            view.width,
            view.height,
            endRadius,
            startRadius
    ).apply {
        duration = 300
        interpolator = AccelerateInterpolator()
    }


    view.visibility = View.VISIBLE
    circularReveal.start()
}

@BindingAdapter("setText")
fun BindAdapterText(view: TextView, str: Int){
    view.text = view.context.getString(R.string.item_price, str)
}

@SuppressLint("SimpleDateFormat")
@BindingAdapter("setDateText")
fun BindAdapterDateText(view: TextView, date: Date?){
    date?.let {
        val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
        view.text = dateFormat.format(it)
    }?: run {
        view.text = ""
    }
}

@BindingAdapter("setToolBarTitle")
fun BindAdapterSetToolBarTitle(view: Toolbar, title: String){
    view.title = title
}
