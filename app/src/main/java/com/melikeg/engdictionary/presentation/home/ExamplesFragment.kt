package com.melikeg.engdictionary.presentation.home

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.melikeg.engdictionary.data.dto.Meaning
import com.melikeg.engdictionary.databinding.FragmentExamplesBinding


class ExamplesFragment : Fragment() {

    private lateinit var meaning: Meaning

    private var _binding: FragmentExamplesBinding? = null
    val binding: FragmentExamplesBinding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentExamplesBinding.inflate(layoutInflater)
        return binding.root
    }

    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        meaning = requireArguments().getSerializable(ARG_MEANING) as Meaning

        val examples = meaning.definitions.map { it.example }

        val examplesText = examples.mapIndexed { index, s ->
            var ss = s
            if(ss.isNullOrEmpty()) ss = "No example for this meaning."
            "${index+1}.$ss"
        }.joinToString("\n\n")

        //if(examplesText == "No example for this meaning.") binding.tvExample.setTextColor(R.color.ecru)
        //else binding.tvExample.setTextColor(R.color.gray)

        binding.tvPospeech.text = meaning.partOfSpeech
        binding.tvExample.text = examplesText



    }

    companion object {
        private const val ARG_MEANING = "arg_meaning"

        fun newInstance(meaning: Meaning): ExamplesFragment {
            val fragment = ExamplesFragment()
            val args = Bundle()
            args.putSerializable(ARG_MEANING, meaning)
            fragment.arguments = args
            return fragment
        }
    }

}