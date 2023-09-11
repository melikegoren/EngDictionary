package com.melikeg.engdictionary.presentation.home

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.melikeg.engdictionary.R
import com.melikeg.engdictionary.databinding.FragmentHomeBinding
import com.melikeg.engdictionary.presentation.adapters.MeaningPagerAdapter
import com.melikeg.engdictionary.slideLeft
import com.melikeg.engdictionary.spannable
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
                    is HomeUiState.Error -> {Toast.makeText(requireContext(), "Error occurred.", Toast.LENGTH_LONG).show()}
                    is HomeUiState.Success -> {
                        binding.tvWord.slideLeft(500L,0)
                        binding.cvPhonetic.slideLeft(500L,0)
                        binding.cvMeanings.slideLeft(500L,0)
                        binding.tvWord.text = it.data.word
                        binding.tvPhonetic.text = it.data.phoneticText

                        //Log.d("sizee", it.data.definitions.size.toString())
                        //Log.d("mean1", it.data.definitions.get(1).get(1).definition.toString())
                        //Log.d("antonyms", it.data.antonyms.get(1).toString())
                        val adapter = MeaningPagerAdapter(childFragmentManager, it.data.meanings)
                        binding.viewpagerMeanings.adapter = adapter
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
                       preparingMediaPlayer(it.data.audioUrl)

                    }


                }
            }
        }



    }

    fun navigateToWiki(){
        val direction = HomeFragmentDirections.actionHomeFragmentToWikiFragment()
        binding.tvMoreInfo.spannable(resources.getString(R.string.click_for_more_info),resources.getString(R.string.click_for_more_info), direction)
    }

    fun preparingMediaPlayer(audioUrl: String){
        mediaPlayer = MediaPlayer()

        mediaPlayer.setAudioAttributes(
            AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build()
        )

        mediaPlayer.setDataSource(audioUrl)
        mediaPlayer.prepareAsync()

        mediaPlayer.setOnPreparedListener{
            it.start()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }


}