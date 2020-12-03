package com.desafiomovilepay.presentation.model

data class CardDetails(
    val cardNumber: String,
    val cardName: String,
    val expirationDate: String,
    val availableLimit: String,
    val totalLimit: String,
)
