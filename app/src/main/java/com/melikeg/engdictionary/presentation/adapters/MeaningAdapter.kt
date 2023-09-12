package com.melikeg.engdictionary.presentation.adapters

import android.view.View
import com.melikeg.engdictionary.R
import com.melikeg.engdictionary.databinding.MeaningItemBinding
import com.melikeg.engdictionary.presentation.home.HomeUiData

class MeaningAdapter: com.melikeg.engdictionary.presentation.base.BaseAdapter<HomeUiData>(R.layout.meaning_item) {
    override fun bind(
        itemView: View,
        item: HomeUiData,
        position: Int,
        viewHolder: BaseViewHolderImpl
    ) {
        val binding = MeaningItemBinding.bind(itemView)

    }

}
