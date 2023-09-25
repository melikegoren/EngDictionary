package com.melikeg.engdictionary.common

import android.widget.Toast
import org.robolectric.annotation.Implementation
import org.robolectric.annotation.Implements
import org.robolectric.annotation.RealObject

@Implements(Toast::class)
class ShadowToast {

    @RealObject
    private lateinit var realToast: Toast

    companion object {
        private var lastToast: Toast? = null

        fun getLatestToast(): Toast? {
            return lastToast
        }
    }

    @Implementation
    fun show() {
        lastToast = realToast
    }
}
