package com.melikeg.engdictionary.common

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.text.SpannableStringBuilder
import android.text.style.BackgroundColorSpan
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.interpolator.view.animation.FastOutLinearInInterpolator
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.melikeg.engdictionary.R

@SuppressLint("ResourceAsColor")
fun TextView.spannable(fullText: String, clickableText: String, direction: NavDirections ){

    val startIndex = fullText.indexOf(clickableText)
    val endIndex = startIndex + clickableText.length

    val spannableStringBuilder = SpannableStringBuilder(fullText)

    val backgroundColorSpan = BackgroundColorSpan(Color.TRANSPARENT)
    spannableStringBuilder.setSpan(backgroundColorSpan, startIndex, endIndex, 0)

    val foregroundColorSpan = ForegroundColorSpan(R.color.raisin_black)
    spannableStringBuilder.setSpan(foregroundColorSpan, startIndex, endIndex, 0)


    val clickableSpan = object : ClickableSpan() {
        override fun onClick(widget: View) {
           findNavController().navigate(direction)
        }
    }
    spannableStringBuilder.setSpan(clickableSpan, startIndex, endIndex, 0)

    this.text = spannableStringBuilder
    this.movementMethod = android.text.method.LinkMovementMethod.getInstance()

}


fun View.slideLeft(animTime: Long, startOffSet: Long){
    val slideLeft = AnimationUtils.loadAnimation(context, R.anim.slide_left).apply {
        duration = animTime
        interpolator = FastOutLinearInInterpolator()
        this.startOffset = startOffset
    }

    startAnimation(slideLeft)
}

@SuppressLint("MissingInflatedId")
fun Context.showCustomToast(message: String, iconResId: Int) {
    val inflater = LayoutInflater.from(this)
    val layout = inflater.inflate(R.layout.custom_toast_layout, null)

    val toastText = layout.findViewById<TextView>(R.id.customToastText)
    toastText.text = message

    val toastIcon = layout.findViewById<ImageView>(R.id.customToastIcon)
    toastIcon.setImageResource(iconResId)

    val toast = Toast(this)
    toast.duration = Toast.LENGTH_SHORT
    toast.setGravity(Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL, 0, 100) // Adjust the position as needed
    toast.view = layout
    toast.show()
}