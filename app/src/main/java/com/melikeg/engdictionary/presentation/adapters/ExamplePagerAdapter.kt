package com.melikeg.engdictionary.presentation.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.melikeg.engdictionary.data.dto.Meaning
import com.melikeg.engdictionary.presentation.home.ExamplesFragment

class ExamplePagerAdapter (fm: FragmentManager, private val meanings: List<Meaning>) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount(): Int {
        return meanings.size
    }


    override fun getItem(position: Int): Fragment {
        val meaning = meanings[position]
        return ExamplesFragment.newInstance(meaning)
    }
}

