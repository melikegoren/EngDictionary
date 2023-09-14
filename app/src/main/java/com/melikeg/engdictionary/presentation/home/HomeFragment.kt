package com.melikeg.engdictionary.presentation.home

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
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

        navigateToWiki()
        bindViewModel()

    }



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

                        val adapterExample = ExamplePagerAdapter(childFragmentManager, it.data.meanings)
                        binding.viewpagerExamples.adapter = adapterExample
                    }

                }
            }
        }

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

    fun navigateToWiki(){
        val action = HomeFragmentDirections.actionHomeFragmentToWikiFragment()
        binding.btnMoreInfo?.setOnClickListener {
            findNavController().navigate(action)
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

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }


}