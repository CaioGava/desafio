package com.desafiomovilepay.presentation.model

data class StatementDetails(
    val balance: Balance,
    val transactions: List<Transaction>,
) {

    data class Balance(
        val label: String,
        val value: String,
    )

    data class Transaction(
        val label: String,
        val value: String,
        val description: String,
    )
}