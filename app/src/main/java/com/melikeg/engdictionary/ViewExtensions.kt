package com.melikeg.engdictionary

import android.graphics.Color
import android.text.SpannableStringBuilder
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.interpolator.view.animation.FastOutLinearInInterpolator
import androidx.navigation.NavDirections
import androidx.navigation.findNavController

fun TextView.spannable(fullText: String, clickableText: String, direction: NavDirections ){

    val startIndex = fullText.indexOf(clickableText)
    val endIndex = startIndex + clickableText.length

    val spannableStringBuilder = SpannableStringBuilder(fullText)

    // Add a BackgroundColorSpan to highlight the clickable part
    /*val backgroundColorSpan = BackgroundColorSpan(Color.TRANSPARENT)
    spannableStringBuilder.setSpan(backgroundColorSpan, startIndex, endIndex, 0)*/

    val foregroundColorSpan = ForegroundColorSpan(Color.GREEN) // Change to the desired color
    spannableStringBuilder.setSpan(foregroundColorSpan, startIndex, endIndex, 0)


    // Add a ClickableSpan to make the clickable part clickable
    val clickableSpan = object : ClickableSpan() {
        override fun onClick(widget: View) {
           findNavController().navigate(direction)
        }
    }
    spannableStringBuilder.setSpan(clickableSpan, startIndex, endIndex, 0)

    // Set the SpannableStringBuilder to the TextView
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