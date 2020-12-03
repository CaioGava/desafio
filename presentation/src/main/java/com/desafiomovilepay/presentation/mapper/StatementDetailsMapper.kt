package com.desafiomovilepay.presentation.mapper

import com.desafiomovilepay.presentation.model.StatementDetails
import com.desafiomovilepay.repository.dto.StatementDetailsDto

object StatementDetailsMapper : Mapper<StatementDetailsDto, StatementDetails> {
    override fun mapFromDto(dto: StatementDetailsDto): StatementDetails {
        return StatementDetails(mapBalance(dto.balance), mapTransactions(dto.transactions))
    }

    private fun mapTransactions(transactionsDto: List<StatementDetailsDto.TransactionDto>): List<StatementDetails.Transaction> {
        val transactions = ArrayList<StatementDetails.Transaction>()
        transactionsDto.mapTo(transactions, {
            StatementDetails.Transaction(it.label, it.value, it.description)
        })

        return transactions
    }

    private fun mapBalance(dto: StatementDetailsDto.BalanceDto): StatementDetails.Balance {
        return StatementDetails.Balance(dto.label, dto.value)
    }
}