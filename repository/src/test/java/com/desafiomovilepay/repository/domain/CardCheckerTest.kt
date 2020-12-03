package com.desafiomovilepay.repository.domain

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CardCheckerTest {

    @InjectMocks
    lateinit var cardChecker: CardChecker

    @Test
    fun `should return true for allowed cardId`() {
        val id = "123"
        assertTrue(cardChecker.isCardIdMarkedAsAllowed(id))
    }

    @Test
    fun `should return false for not allowed cardIds in list`() {
        val ids = listOf("1 2 3", "456", "", "!@#$")

        ids.forEach {
            assertFalse(cardChecker.isCardIdMarkedAsAllowed(it))
        }
    }
}