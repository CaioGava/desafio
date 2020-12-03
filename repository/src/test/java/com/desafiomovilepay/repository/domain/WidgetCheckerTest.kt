package com.desafiomovilepay.repository.domain

import com.desafiomovilepay.repository.dto.WidgetDto
import com.desafiomovilepay.repository.dto.WidgetIdentifier
import com.google.gson.internal.LinkedTreeMap
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class WidgetCheckerTest {

    @InjectMocks
    lateinit var widgetChecker: WidgetChecker

    @Test
    fun `should filter list successfully`() {
        val widgets = listOf(
            WidgetDto(null, LinkedTreeMap()),
            WidgetDto(WidgetIdentifier.HOME_HEADER_WIDGET, LinkedTreeMap()),
            WidgetDto(WidgetIdentifier.HOME_CARD_WIDGET, LinkedTreeMap()),
            WidgetDto(WidgetIdentifier.HOME_STATEMENT_WIDGET, LinkedTreeMap()),
        )

        val results = widgetChecker.filterForAllowedWidgets(widgets)

        assertEquals(3, results.size)
        assertEquals(WidgetIdentifier.HOME_HEADER_WIDGET, results[0].identifier)
        assertEquals(WidgetIdentifier.HOME_CARD_WIDGET, results[1].identifier)
        assertEquals(WidgetIdentifier.HOME_STATEMENT_WIDGET, results[2].identifier)
    }
}