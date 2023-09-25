package com.melikeg.engdictionary.common

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import com.melikeg.engdictionary.R
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(shadows = [ShadowToast::class], sdk = [33])
class ViewExtensionsTest{
    private lateinit var context: Context

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun testShowCustomToast() {
        val message = "Test Message"
        val iconResId = R.drawable.baseline_warning_amber_24

        context.showCustomToast(message, iconResId)

        val toast = ShadowToast.getLatestToast()

        assertNotNull(toast)

        val toastView = toast?.view
        val toastText = toastView?.findViewById<TextView>(R.id.customToastText)
        val toastIcon = toastView?.findViewById<ImageView>(R.id.customToastIcon)

        assertNotNull(toastText)
        assertNotNull(toastIcon)
        assertEquals(message, toastText?.text.toString())

    }
}