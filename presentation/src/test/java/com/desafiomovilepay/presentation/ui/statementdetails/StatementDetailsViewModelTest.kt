package com.desafiomovilepay.presentation.ui.statementdetails

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.desafiomovilepay.presentation.MainCoroutineRule
import com.desafiomovilepay.presentation.StatementDetailsUtil
import com.desafiomovilepay.repository.Response
import com.desafiomovilepay.repository.domain.AccountChecker
import com.desafiomovilepay.repository.gateway.statement.IStatementRepository
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
class StatementDetailsViewModelTest {

    @get:Rule
    val rule = MainCoroutineRule()

    @get:Rule
    val instant = InstantTaskExecutorRule()

    @InjectMockKs
    private lateinit var viewModel: StatementDetailsViewModel

    @MockK
    private lateinit var reposirory: IStatementRepository

    @MockK
    private lateinit var allowedChecker: AccountChecker

    @MockK
    private lateinit var savedStateHandle: SavedStateHandle

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `should start with an empty state`() {
        assertTrue(viewModel.state.value == StatementDetailsViewModel.State.Empty)
    }

    @Test
    fun `should change to an error state when the given accountId is not 123`() {
        every { allowedChecker.isAccountIdMarkedAsAllowed("456") } returns false

        viewModel.requestStatementDetails("456")

        assertTrue(viewModel.state.value == StatementDetailsViewModel.State.Error("AccountId must be 123"))
        coVerify(exactly = 0) {
            reposirory.getStatementDetails("456")
        }
    }

    @Test
    fun `should change to a success state when research get results successfully`() {
        val expectedDto = StatementDetailsUtil.provideStatementDetailsDto()

        every { allowedChecker.isAccountIdMarkedAsAllowed("123") } returns true

        coEvery { reposirory.getStatementDetails("123") } returns
                flowOf(Response.Success(expectedDto))

        rule.runBlockingTest { viewModel.requestStatementDetails("123") }

        assertTrue(viewModel.state.value is StatementDetailsViewModel.State.Success)
        val response = viewModel.state.value as StatementDetailsViewModel.State.Success

        response.details.balance.apply {
            assertEquals(expectedDto.balance.label, label)
            assertEquals(expectedDto.balance.value, value)
        }
    }

    @Test
    fun `should change to an error state when repository sends an error`() {
        every { allowedChecker.isAccountIdMarkedAsAllowed("123") } returns true

        coEvery { reposirory.getStatementDetails("123") } returns
                flowOf(Response.Error(Exception()))

        rule.runBlockingTest {
            viewModel.requestStatementDetails("123")
        }

        assertTrue(viewModel.state.value is StatementDetailsViewModel.State.Error)
        val response = viewModel.state.value as StatementDetailsViewModel.State.Error
        assertEquals("Ooops! Something went wrong.", response.msg)
    }
}