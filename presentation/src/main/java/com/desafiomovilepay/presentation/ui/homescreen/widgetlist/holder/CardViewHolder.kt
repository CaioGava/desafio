package com.desafiomovilepay.presentation.ui.homescreen.widgetlist.holder

import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.desafiomovilepay.presentation.databinding.WidgetCardBinding
import com.desafiomovilepay.presentation.ui.homescreen.HomeScreenFragmentDirections
import com.desafiomovilepay.repository.dto.WidgetDto
import com.google.gson.internal.LinkedTreeMap

class CardViewHolder(private val binding: WidgetCardBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(widget: WidgetDto) {
        binding.apply {
            tvMyCardLabel.text = widget.content["title"].toString()
            tvCardNumber.text = widget.content["cardNumber"].toString()
            val button = widget.content["button"] as LinkedTreeMap<*, *>
            btnDetails.text = button["text"].toString()

            btnDetails.setOnClickListener {
                val buttonAction = button["action"] as LinkedTreeMap<*, *>
                val buttonContent = buttonAction["content"] as LinkedTreeMap<*, *>
                val action =
                    HomeScreenFragmentDirections.actionHomeScreenFragmentToCardDetailsFragment(
                        buttonContent["cardId"].toString()
                    )
                val navController = Navigation.findNavController(itemView)
                navController.navigate(action)
            }
        }
    }
}