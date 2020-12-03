package com.desafiomovilepay.repository.domain

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AccountCheckerTest {

    @InjectMocks
    lateinit var accountChecker: AccountChecker

    @Test
    fun `should return true for allowed accountId`() {
        val id = "123"
        assertTrue(accountChecker.isAccountIdMarkedAsAllowed(id))
    }

    @Test
    fun `should return false for not allowed accountIds in list`() {
        val ids = listOf("1 2 3", "456", "", "!@#$")

        ids.forEach {
            assertFalse(accountChecker.isAccountIdMarkedAsAllowed(it))
        }
    }
}