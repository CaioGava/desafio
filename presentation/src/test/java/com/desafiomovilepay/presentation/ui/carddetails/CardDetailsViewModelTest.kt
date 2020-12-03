package com.desafiomovilepay.presentation.ui.carddetails

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.desafiomovilepay.presentation.CardDetailsUtil
import com.desafiomovilepay.presentation.MainCoroutineRule
import com.desafiomovilepay.repository.Response
import com.desafiomovilepay.repository.domain.CardChecker
import com.desafiomovilepay.repository.gateway.card.ICardRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
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
class CardDetailsViewModelTest {

    @get:Rule
    val rule = MainCoroutineRule()

    @get:Rule
    val instant = InstantTaskExecutorRule()

    @InjectMockKs
    private lateinit var viewModel: CardDetailsViewModel

    @MockK
    private lateinit var reposirory: ICardRepository

    @MockK
    private lateinit var cardChecker: CardChecker

    @MockK
    private lateinit var savedStateHandle: SavedStateHandle

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `should start with an empty state`() {
        assertTrue(viewModel.state.value == CardDetailsViewModel.State.Empty)
    }

    @Test
    fun `should change to an error state when the given cardId is not 123`() {
        every { cardChecker.isCardIdMarkedAsAllowed("456") } returns false

        viewModel.requestCardDetails("456")

        assertTrue(viewModel.state.value == CardDetailsViewModel.State.Error("CardId must be 123"))
        coVerify(exactly = 0) {
            reposirory.getCardDetails("456")
        }
    }

    @Test
    fun `should change to a success state when research get results successfully`() {
        val expectedDto = CardDetailsUtil.provideCardDetailsDto()

        every { cardChecker.isCardIdMarkedAsAllowed("123") } returns true

        coEvery { reposirory.getCardDetails("123") } returns
                flowOf(Response.Success(expectedDto))

        rule.runBlockingTest { viewModel.requestCardDetails("123") }

        assertTrue(viewModel.state.value is CardDetailsViewModel.State.Success)
        val response = viewModel.state.value as CardDetailsViewModel.State.Success
        response.cardDetails.apply {
            assertEquals(expectedDto.cardNumber, this.cardNumber)
            assertEquals(expectedDto.cardName, this.cardName)
            assertEquals(expectedDto.expirationDate, this.expirationDate)
            assertEquals(expectedDto.availableLimit, this.availableLimit)
            assertEquals(expectedDto.totalLimit, this.totalLimit)
        }
    }

    @Test
    fun `should change to an error state when repository sends an error`() {
        every { cardChecker.isCardIdMarkedAsAllowed("123") } returns true

        coEvery { reposirory.getCardDetails("123") } returns
                flowOf(Response.Error(Exception()))

        rule.runBlockingTest {
            viewModel.requestCardDetails("123")
        }

        assertTrue(viewModel.state.value is CardDetailsViewModel.State.Error)
        val response = viewModel.state.value as CardDetailsViewModel.State.Error
        assertEquals("Ooops! Something went wrong.", response.msg)
    }
}