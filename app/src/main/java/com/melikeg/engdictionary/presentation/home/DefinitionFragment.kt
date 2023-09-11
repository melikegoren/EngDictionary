package com.melikeg.engdictionary.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.melikeg.engdictionary.data.dto.Meaning
import com.melikeg.engdictionary.databinding.FragmentDefinitionBinding


class DefinitionFragment : Fragment() {

    private lateinit var meaning: Meaning

    private var _binding: FragmentDefinitionBinding? = null
    val binding: FragmentDefinitionBinding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDefinitionBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        meaning = requireArguments().getSerializable(ARG_MEANING) as Meaning
        val definitions = meaning.definitions.map { it.definition }
        //val examples = meaning.definitions.map { it.example }

        val definitionsText = definitions.joinToString("\n\n-") // Concatenate definitions with newlines
        //val examplesText = examples.joinToString("\n\n")
        binding.tvPospeech.text = meaning.partOfSpeech
        binding.tvDefinition.text = "- "+definitionsText


    }

    companion object {
        private const val ARG_MEANING = "arg_meaning"

        fun newInstance(meaning: Meaning): DefinitionFragment {
            val fragment = DefinitionFragment()
            val args = Bundle()
            args.putSerializable(ARG_MEANING, meaning)
            fragment.arguments = args
            return fragment
        }
    }

}