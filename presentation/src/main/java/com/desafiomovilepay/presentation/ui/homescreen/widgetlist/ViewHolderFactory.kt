package com.desafiomovilepay.presentation.ui.homescreen.widgetlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.desafiomovilepay.presentation.databinding.WidgetCardBinding
import com.desafiomovilepay.presentation.databinding.WidgetHeaderBinding
import com.desafiomovilepay.presentation.databinding.WidgetStatementBinding
import com.desafiomovilepay.presentation.ui.homescreen.widgetlist.holder.CardViewHolder
import com.desafiomovilepay.presentation.ui.homescreen.widgetlist.holder.HeaderViewHolder
import com.desafiomovilepay.presentation.ui.homescreen.widgetlist.holder.StatementViewHolder
import com.desafiomovilepay.repository.dto.WidgetIdentifier

object ViewHolderFactory {

    fun create(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            WidgetIdentifier.HOME_HEADER_WIDGET.ordinal -> {
                HeaderViewHolder(
                    WidgetHeaderBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            }
            WidgetIdentifier.HOME_CARD_WIDGET.ordinal -> {
                CardViewHolder(
                    WidgetCardBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            }
            else -> {
                StatementViewHolder(
                    WidgetStatementBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            }
        }
    }
}