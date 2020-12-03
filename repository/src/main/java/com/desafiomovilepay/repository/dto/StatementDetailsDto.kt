package com.desafiomovilepay.repository.dto

data class StatementDetailsDto(
    val balance: BalanceDto,
    val transactions: List<TransactionDto>,
) {

    data class BalanceDto(
        val label: String,
        val value: String,
    )

    data class TransactionDto(
        val label: String,
        val value: String,
        val description: String,
    )
}




