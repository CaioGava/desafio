package com.desafiomovilepay.repository

import com.desafiomovilepay.repository.dto.StatementDetailsDto
import com.desafiomovilepay.repository.gateway.statement.StatementApiGateway
import com.desafiomovilepay.repository.gateway.statement.StatementRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class StatementRepositoryTest {

    @get:Rule
    val rule = MainCoroutineRule()

    @Mock
    lateinit var gateway: StatementApiGateway

    lateinit var repository: StatementRepository

    @Before
    fun setup() {
        repository = StatementRepository(gateway, rule.testDispatcher)
    }

    @Test
    fun `should return card info successfully`() {
        rule.runBlockingTest {
            val expectedDto = StatementDetailsUtil.provideStatementDetailsDto()

            Mockito.`when`(gateway.getStatementDetails("123"))
                .thenReturn(expectedDto)

            repository.getStatementDetails("123").collect {
                assertTrue(it is Response.Success)
                val response: StatementDetailsDto =
                    (it as Response.Success).dto as StatementDetailsDto

                with(response) {
                    with(balance) {
                        assertEquals(label, expectedDto.balance.label)
                        assertEquals(value, expectedDto.balance.value)
                    }

                    transactions.zip(expectedDto.transactions).forEach { pair ->
                        assertEquals(pair.first, pair.second)
                    }
                }
            }
        }
    }

    @Test
    fun `should return error when an exception is thrown`() {
        rule.runBlockingTest {
            Mockito.`when`(gateway.getStatementDetails("123"))
                .thenThrow(NullPointerException::class.java)

            repository.getStatementDetails("123").collect {
                assertTrue(it is Response.Error)
                val response: Throwable = (it as Response.Error).error
                assertTrue(response is NullPointerException)
            }
        }
    }
}