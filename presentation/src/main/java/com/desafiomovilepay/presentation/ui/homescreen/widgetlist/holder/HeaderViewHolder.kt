package com.desafiomovilepay.presentation.ui.homescreen.widgetlist.holder

import androidx.recyclerview.widget.RecyclerView
import com.desafiomovilepay.presentation.databinding.WidgetHeaderBinding
import com.desafiomovilepay.repository.dto.WidgetDto

class HeaderViewHolder(private val binding: WidgetHeaderBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(widget: WidgetDto) {
        binding.tvGreetingValue.text = widget.content["title"].toString()
    }
}
