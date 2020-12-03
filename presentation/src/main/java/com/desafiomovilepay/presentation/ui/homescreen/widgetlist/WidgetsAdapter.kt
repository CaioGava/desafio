package com.desafiomovilepay.presentation.ui.homescreen.widgetlist

import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.desafiomovilepay.presentation.ui.homescreen.widgetlist.holder.CardViewHolder
import com.desafiomovilepay.presentation.ui.homescreen.widgetlist.holder.HeaderViewHolder
import com.desafiomovilepay.presentation.ui.homescreen.widgetlist.holder.StatementViewHolder
import com.desafiomovilepay.repository.dto.WidgetDto
import com.desafiomovilepay.repository.dto.WidgetIdentifier

class WidgetsAdapter(
    private val widgets: LiveData<List<WidgetDto>>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolderFactory.create(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val current = widgets.value?.get(position) ?: return

        return when (getItemViewType(position)) {
            WidgetIdentifier.HOME_HEADER_WIDGET.ordinal ->
                (holder as HeaderViewHolder).bind(current)
            WidgetIdentifier.HOME_CARD_WIDGET.ordinal ->
                (holder as CardViewHolder).bind(current)
            else -> (holder as StatementViewHolder).bind(current)
        }
    }

    override fun getItemCount(): Int {
        return widgets.value?.size ?: 0
    }

    override fun getItemViewType(position: Int): Int {
        return when (widgets.value?.get(position)?.identifier) {
            WidgetIdentifier.HOME_HEADER_WIDGET -> WidgetIdentifier.HOME_HEADER_WIDGET.ordinal
            WidgetIdentifier.HOME_CARD_WIDGET -> WidgetIdentifier.HOME_CARD_WIDGET.ordinal
            else -> WidgetIdentifier.HOME_STATEMENT_WIDGET.ordinal
        }
    }
}
