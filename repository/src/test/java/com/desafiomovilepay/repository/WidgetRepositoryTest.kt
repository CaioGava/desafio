package com.desafiomovilepay.repository

import com.desafiomovilepay.repository.domain.WidgetChecker
import com.desafiomovilepay.repository.dto.WidgetDto
import com.desafiomovilepay.repository.gateway.widget.WidgetApiGateway
import junit.framework.Assert.assertNotNull
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class WidgetRepositoryTest {

    @get:Rule
    val rule = MainCoroutineRule()

    @Mock
    lateinit var gateway: WidgetApiGateway

    lateinit var widgetChecker: WidgetChecker

    lateinit var repository: WidgetRepository

    @Before
    fun setup() {
        widgetChecker = mock(WidgetChecker::class.java) // Having problems to mock widget
        repository = WidgetRepository(gateway, widgetChecker, rule.testDispatcher)
    }

    @Test
    fun `should return widget info successfully`() {
        val expectedDto = WidgetsUtil.provideWidgets()

        rule.runBlockingTest {

            Mockito.`when`(gateway.getWidgets())
                .thenReturn(expectedDto)

            // should mock widgetChecker.filterForAllowedWidgets() here
            // but no success so far

            repository.getWidgets().collect { it ->
                assertTrue(it is Response.Success)
                val response = (it as Response.Success).dto as List<WidgetDto>

                response.forEach { item ->
                    assertNotNull(item.identifier)
                }
            }
        }
    }

    @Test
    fun `should return error when an exception is thrown`() {
        rule.runBlockingTest {
            Mockito.`when`(gateway.getWidgets())
                .thenThrow(NullPointerException::class.java)

            repository.getWidgets().collect {
                assertTrue(it is Response.Error)
                val response: Throwable = (it as Response.Error).error
                assertTrue(response is NullPointerException)
            }
        }
    }
}