package com.desafiomovilepay.presentation.ui.homescreen

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.desafiomovilepay.presentation.MainCoroutineRule
import com.desafiomovilepay.presentation.WidgetsUtil
import com.desafiomovilepay.repository.IWidgetRepository
import com.desafiomovilepay.repository.Response
import com.desafiomovilepay.repository.dto.WidgetDto

import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeScreenViewModelTest {

    @get:Rule
    val rule = MainCoroutineRule()

    @get:Rule
    val instant = InstantTaskExecutorRule()

    @InjectMockKs
    private lateinit var viewModel: HomeScreenViewModel

    @MockK
    private lateinit var repository: IWidgetRepository

    @MockK
    private lateinit var savedStateHandle: SavedStateHandle

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `should return a list containing only elements declared WidgetIdentifier successfully`() {
        val expectedDto = WidgetsUtil.provideWidgets().widgets

        coEvery { repository.getWidgets() } returns
                flowOf(Response.Success(WidgetsUtil.provideWidgets().widgets))

        rule.runBlockingTest { viewModel.getWidgets() }

        expectedDto.zip(viewModel.allWidgets.value as List<WidgetDto>).forEach { pair ->
            assertEquals(pair.first.identifier, pair.second.identifier)
            assertEquals(pair.first.identifier, pair.second.identifier)
        }
    }

    @Test
    fun `should return an empty list when repository sends an error`() {
        coEvery { repository.getWidgets() } returns
                flowOf(Response.Error(Exception()))

        rule.runBlockingTest { viewModel.getWidgets() }

        assertTrue(viewModel.allWidgets.value!!.isEmpty())
    }

}