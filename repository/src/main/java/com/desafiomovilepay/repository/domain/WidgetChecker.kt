package com.desafiomovilepay.repository.domain

import com.desafiomovilepay.repository.dto.WidgetDto
import com.desafiomovilepay.repository.dto.WidgetIdentifier
import javax.inject.Inject

open class WidgetChecker @Inject constructor() {

    fun filterForAllowedWidgets(widgets: List<WidgetDto>): List<WidgetDto> {
        return widgets.filter {
            WidgetIdentifier.values().contains(it.identifier)
        }
    }
}