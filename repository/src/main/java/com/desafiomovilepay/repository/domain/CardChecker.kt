package com.desafiomovilepay.repository.domain

import javax.inject.Inject

class CardChecker @Inject constructor() {
    private val allowedList: List<String> = listOf("123")

    fun isCardIdMarkedAsAllowed(cardId: String): Boolean {
        return allowedList.contains(cardId)
    }
}