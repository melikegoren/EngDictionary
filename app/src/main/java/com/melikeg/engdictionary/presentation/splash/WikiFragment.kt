package com.melikeg.engdictionary.presentation.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.melikeg.engdictionary.databinding.FragmentWikiBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WikiFragment : Fragment() {

    private var _binding: FragmentWikiBinding? = null
    val binding: FragmentWikiBinding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentWikiBinding.inflate(layoutInflater)
        return binding.root
    }

}