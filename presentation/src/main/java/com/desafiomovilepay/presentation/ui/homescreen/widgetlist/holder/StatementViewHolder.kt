package com.desafiomovilepay.presentation.ui.homescreen.widgetlist.holder

import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.desafiomovilepay.presentation.databinding.WidgetStatementBinding
import com.desafiomovilepay.presentation.ui.homescreen.HomeScreenFragmentDirections
import com.desafiomovilepay.repository.dto.WidgetDto
import com.google.gson.internal.LinkedTreeMap

class StatementViewHolder(private val binding: WidgetStatementBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(widget: WidgetDto) {
        binding.apply {
            val balance = widget.content["balance"] as LinkedTreeMap<*, *>
            tvMyBalanceLabel.text = widget.content["title"].toString()
            tvAvailableBalanceLabel.text = balance["label"].toString()
            tvAvailableBalanceValue.text = balance["value"].toString()

            val button = widget.content["button"] as LinkedTreeMap<*, *>
            val buttonAction = button["action"] as LinkedTreeMap<*, *>
            btnDetails.text = button["text"].toString()

            btnDetails.setOnClickListener {
                val buttonContent = buttonAction["content"] as LinkedTreeMap<*, *>
                val action =
                    HomeScreenFragmentDirections.actionHomeScreenFragmentToStatementDetailsFragment(
                        buttonContent["accountId"].toString()
                    )

                val navController = Navigation.findNavController(itemView)
                navController.navigate(action)
            }
        }
    }
}