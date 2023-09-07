package com.melikeg.engdictionary.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.melikeg.engdictionary.R
import com.melikeg.engdictionary.databinding.FragmentHomeBinding
import com.melikeg.engdictionary.spannable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

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
                    is HomeUiState.Error -> {Toast.makeText(requireContext(), "Error occured.", Toast.LENGTH_LONG).show()}
                    is HomeUiState.Success -> {
                        binding.tvWord.text = it.data.word
                    }

                }
            }
        }

    }

    fun navigateToWiki(){
        val direction = HomeFragmentDirections.actionHomeFragmentToWikiFragment()
        binding.tvMoreInfo.spannable(resources.getString(R.string.click_for_more_info),resources.getString(R.string.click_for_more_info), direction)
    }


}