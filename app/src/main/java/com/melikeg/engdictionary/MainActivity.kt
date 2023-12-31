package com.melikeg.engdictionary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.melikeg.engdictionary.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    val binding: ActivityMainBinding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
