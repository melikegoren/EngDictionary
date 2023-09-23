package com.melikeg.engdictionary.presentation.home

import android.annotation.SuppressLint
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.melikeg.engdictionary.R
import com.melikeg.engdictionary.databinding.FragmentHomeBinding
import com.melikeg.engdictionary.presentation.adapters.ExamplePagerAdapter
import com.melikeg.engdictionary.presentation.adapters.MeaningPagerAdapter
import com.melikeg.engdictionary.showCustomToast
import com.melikeg.engdictionary.slideLeft
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment: Fragment() {

    private var _binding: FragmentHomeBinding? = null
    val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var mediaPlayer: MediaPlayer





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindViewModel()
        showBottomSheetDialog()
        clearText()


    }



    @SuppressLint("SetJavaScriptEnabled")
    fun bindViewModel(){
        binding.btnGo.setOnClickListener {
            viewModel.getWord(binding.searchview.text.toString())

            viewModel.wordDataUiState.observe(viewLifecycleOwner){
                when(it){
                    is HomeUiState.Loading -> {}
                    is HomeUiState.Error -> {

                        requireContext().showCustomToast("Invalid word or network error.", R.drawable.baseline_warning_amber_24)}
                    is HomeUiState.Success -> {
                        binding.tvWord.slideLeft(500L,0)
                        binding.cvPhonetic.slideLeft(500L,0)
                        binding.cvMeanings.slideLeft(500L,0)
                        binding.cvExamples.slideLeft(500L,0)

                        binding.tvWord.text = it.data.word



                        Log.d("audioUrl", it.data.audioUrl.size.toString())
                        Log.d("audioUrl", it.data.phoneticText.size.toString())

                        var a = 0
                        for(i in 0 until it.data.audioUrl.size){
                            if(it.data.phoneticText[i].isNullOrEmpty() || it.data.audioUrl[i].isNullOrEmpty()){
                                a++
                                continue
                            }
                            else{
                                binding.tvPhonetic.text = it.data.phoneticText[i]
                                break
                            }
                        }

                        Log.d("aaa", a.toString())
                        if(a == it.data.audioUrl.size) {
                            binding.tvPhonetic.text = "Can't find any phonetic transcription."
                        }

                        binding.linearLayDef.visibility = View.GONE
                        binding.linearLayDef2.visibility = View.GONE

                        val adapterDefinition = MeaningPagerAdapter(childFragmentManager, it.data.meanings)
                        binding.viewpagerMeanings.adapter = adapterDefinition
                        createIndicators(it.data.meanings.size, binding.dotsLayout)
                        Log.d("sizee", it.data.meanings.size.toString())
                        binding.viewpagerMeanings.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
                            override fun onPageScrolled(
                                position: Int,
                                positionOffset: Float,
                                positionOffsetPixels: Int
                            ) {}

                            override fun onPageSelected(position: Int) {
                                setCurrentIndicator(position, binding.dotsLayout)
                            }

                            override fun onPageScrollStateChanged(state: Int) {}

                        })

                        val adapterExample = ExamplePagerAdapter(childFragmentManager, it.data.meanings)
                        binding.viewpagerExamples.adapter = adapterExample
                        createIndicators(it.data.meanings.size, binding.dotsLayout2)
                        binding.viewpagerExamples.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
                            override fun onPageScrolled(
                                position: Int,
                                positionOffset: Float,
                                positionOffsetPixels: Int
                            ) {}

                            override fun onPageSelected(position: Int) {
                                setCurrentIndicator(position, binding.dotsLayout2)
                            }

                            override fun onPageScrollStateChanged(state: Int) {}

                        })
                    }

                }
            }
        }

        /*binding.btnMoreInfo?.setOnClickListener {
            viewModel.wordDataUiState.observe(viewLifecycleOwner){
                when(it) {
                    is HomeUiState.Loading -> {}
                    is HomeUiState.Error -> {
                        Toast.makeText(requireContext(), "Error occurred.", Toast.LENGTH_LONG)
                            .show()
                    }

                    is HomeUiState.Success -> {
                        val bottomSheetFragment = CustomBottomSheetFragment()
                        val url = it.data.sourceUrl
                        bottomSheetFragment.binding.webView.loadUrl(url)
                        bottomSheetFragment.show(requireActivity().supportFragmentManager, bottomSheetFragment.tag)
                    }
                }
            }
        }*/



        binding.btnAudio.setOnClickListener {
            viewModel.wordDataUiState.observe(viewLifecycleOwner){
                when(it){
                    is HomeUiState.Loading -> {}
                    is HomeUiState.Error -> {Toast.makeText(requireContext(), "Error occurred.", Toast.LENGTH_LONG).show()}
                    is HomeUiState.Success -> {

                           for(i in 0 until it.data.audioUrl.size){
                               if(it.data.audioUrl[i].isNotEmpty()) {
                                   preparingMediaPlayer(it.data.audioUrl[i])
                                   break
                               }
                           }
                    }
                }
            }

            viewModel.wordDataUiState.removeObservers(viewLifecycleOwner)

        }

    }

    @SuppressLint("ClickableViewAccessibility")
    private fun clearText(){
        binding.searchview.setOnTouchListener { v, event ->
            val drawableEnd = 2// drawableRight konumunu belirtir
            if (event.action == MotionEvent.ACTION_UP) {
                val totalPaddingEnd = binding.searchview.totalPaddingEnd
                val clearButtonStart = binding.searchview.width - totalPaddingEnd
                if (event.rawX >= clearButtonStart) {
                    // Drawable'a tıklandı, EditText'teki yazıyı temizle
                    //clearBindings()
                    binding.searchview.text = null
                    return@setOnTouchListener true
                } }
            false
        }
    }

    private fun clearBindings(){
        binding.tvPhonetic.text = ""
        binding.dotsLayout.removeAllViews()
        binding.dotsLayout2.removeAllViews()
        binding.cvMeanings.removeAllViews()
        binding.cvExamples.removeAllViews()

    }

    @SuppressLint("MissingInflatedId", "SetJavaScriptEnabled")
    private fun showBottomSheetDialog() {
        val dialogView = layoutInflater.inflate(R.layout.bottom_sheet_layout, null)
        val dialog = BottomSheetDialog(requireContext())
        dialog.setContentView(dialogView)



        val webView = dialogView.findViewById<WebView>(R.id.webView)

        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true // Enable JavaScript
        webSettings.domStorageEnabled = true // Enable DOM storage
        webSettings.loadWithOverviewMode = true
        webSettings.useWideViewPort = true
        webSettings.setSupportZoom(true)
        val closeButton = dialogView.findViewById<ImageButton>(R.id.btn_close)

        binding.btnMoreInfo?.setOnClickListener {
            dialogView.computeScroll()

            webView.webViewClient = MyWebViewClient()
            viewModel.wordDataUiState.observe(viewLifecycleOwner){
                viewModel.getWord(binding.searchview.text.toString())
                when(it) {
                    is HomeUiState.Loading -> {}
                    is HomeUiState.Error -> {
                        Toast.makeText(requireContext(), "Error occurred.", Toast.LENGTH_LONG)
                            .show()
                    }
                    is HomeUiState.Success -> {

                        val url = it.data.sourceUrl
                        webView.loadUrl(url)
                    }
                }
            }


            dialog.setOnShowListener { dialogInterface ->
                val bottomSheetDialog = dialogInterface as BottomSheetDialog
                val bottomSheetInternal = bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
                BottomSheetBehavior.from(dialogView.parent as View).skipCollapsed = true
            }
            dialog.show()
            viewModel.wordDataUiState.removeObservers(viewLifecycleOwner)

        }

        closeButton.setOnClickListener {
            dialog.dismiss()
        }




    }

    private inner class MyWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            // Load the clicked URL in the WebView itself
            if (url != null) {
                view?.loadUrl(url)
            }
            return true
        }
    }




    fun preparingMediaPlayer(audioUrl: String) {

        val mediaPlayer = MediaPlayer()

        mediaPlayer.setAudioAttributes(
            AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build()
        )


        mediaPlayer.setDataSource(audioUrl)
        mediaPlayer.prepareAsync()

        mediaPlayer.setOnPreparedListener {
            it.start()
        }
        mediaPlayer.pause()

    }

    private fun createIndicators(count: Int, linearLayout: LinearLayout) {
        linearLayout.removeAllViews()
        for (i in 0 until count) {
            val indicatorView = View(requireContext())
            val params = LinearLayout.LayoutParams(
                resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._6sdp),
                resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._6sdp)
            )
            params.setMargins(
                resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._3sdp),
                0,
                resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._3sdp),
                0
            )
            indicatorView.layoutParams = params
            indicatorView.setBackgroundResource(R.drawable.indicator_dot_selector)

            linearLayout.addView(indicatorView)
        }
    }

    private fun setCurrentIndicator(position: Int, linearLayout: LinearLayout) {
        for (i in 0 until linearLayout.childCount) {
            val indicator = linearLayout.getChildAt(i)
            indicator.isSelected = i == position
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }


}

// ecru   -  #BFA473