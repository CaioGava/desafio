package com.desafiomovilepay.repository

import com.desafiomovilepay.repository.Response.Success
import com.desafiomovilepay.repository.dto.CardDetailsDto
import com.desafiomovilepay.repository.gateway.card.CardApiGateway
import com.desafiomovilepay.repository.gateway.card.CardRepository
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
class CardRepositoryTest {

    @get:Rule
    val rule = MainCoroutineRule()

    @Mock
    lateinit var gateway: CardApiGateway

    lateinit var repository: CardRepository

    @Before
    fun setup() {
        repository = CardRepository(gateway, rule.testDispatcher)
    }

    @Test
    fun `should return card info successfully`() {
        rule.runBlockingTest {
            val expectedDto = CardDetailsUtil.provideCardDetailsDto()

            Mockito.`when`(gateway.getCardDetails("123"))
                .thenReturn(expectedDto)

            repository.getCardDetails("123").collect {
                assertTrue(it is Success)
                val response: CardDetailsDto = (it as Success).dto as CardDetailsDto

                with(response) {
                    assertEquals(cardNumber, expectedDto.cardNumber)
                    assertEquals(cardName, expectedDto.cardName)
                    assertEquals(expirationDate, expectedDto.expirationDate)
                    assertEquals(availableLimit, expectedDto.availableLimit)
                    assertEquals(totalLimit, expectedDto.totalLimit)
                }
            }
        }
    }

    @Test
    fun `should return error when an exception is thrown`() {
        rule.runBlockingTest {
            Mockito.`when`(gateway.getCardDetails("123"))
                .thenThrow(NullPointerException::class.java)

            repository.getCardDetails("123").collect {
                assertTrue(it is Response.Error)
                val response: Throwable = (it as Response.Error).error
                assertTrue(response is NullPointerException)
            }
        }
    }
}