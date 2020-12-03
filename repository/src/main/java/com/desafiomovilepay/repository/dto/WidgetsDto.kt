package com.desafiomovilepay.repository.dto

import com.google.gson.internal.LinkedTreeMap

enum class ActIdentifier {
    CARD_SCREEN,
    STATEMENT_SCREEN
}

enum class WidgetIdentifier {
    HOME_HEADER_WIDGET,
    HOME_CARD_WIDGET,
    HOME_STATEMENT_WIDGET
}

data class WidgetsDto(val widgets: List<WidgetDto>)

class WidgetDto(
    val identifier: WidgetIdentifier?,
    val content: LinkedTreeMap<String, Any>
)
