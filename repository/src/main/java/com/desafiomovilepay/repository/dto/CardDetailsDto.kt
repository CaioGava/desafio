package com.desafiomovilepay.repository.dto

data class CardDetailsDto(
    val cardNumber: String,
    val cardName: String,
    val expirationDate: String,
    val availableLimit: String,
    val totalLimit: String,
)