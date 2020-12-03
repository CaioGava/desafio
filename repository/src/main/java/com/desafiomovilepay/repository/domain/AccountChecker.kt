package com.desafiomovilepay.repository.domain

import javax.inject.Inject

class AccountChecker @Inject constructor() {
    private val allowedList: List<String> = listOf("123")

    fun isAccountIdMarkedAsAllowed(accountId: String): Boolean {
        return allowedList.contains(accountId)
    }
}