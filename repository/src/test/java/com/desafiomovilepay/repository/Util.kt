package com.desafiomovilepay.repository

import com.desafiomovilepay.repository.dto.*
import com.google.gson.internal.LinkedTreeMap


object CardDetailsUtil {

    fun provideCardDetailsDto(): CardDetailsDto {
        return CardDetailsDto(
            "•••• •••• •••• 8765",
            "Jhon Doe",
            "09/25",
            "R$ 3.000,00",
            "R$ 5.000,00",
        )
    }
}

object StatementDetailsUtil {

    fun provideStatementDetailsDto(): StatementDetailsDto {
        return StatementDetailsDto(
            provideBalance(),
            provideTransactions()
        )
    }

    private fun provideBalance(): StatementDetailsDto.BalanceDto {
        return StatementDetailsDto.BalanceDto("Saldo disponível", "R$ 50.000,00")
    }

    private fun provideTransactions(): List<StatementDetailsDto.TransactionDto> {
        return listOf(
            StatementDetailsDto.TransactionDto(
                "Transferência enviada",
                "- R$ 500,00",
                "Teste fulano ciclano"
            ),
            StatementDetailsDto.TransactionDto(
                "Pagamento realizado",
                "- R\$ 645,00",
                "Teste fulano ciclano"
            ),
            StatementDetailsDto.TransactionDto(
                "Transferência recebida",
                "+ R\$ 2000,00",
                "Movile Pay"
            ),
        )
    }
}

object WidgetsUtil {

    fun provideWidgets(): WidgetsDto {
        return WidgetsDto(createWidgets())
    }

    private fun createWidgets(): List<WidgetDto> {
        val content = LinkedTreeMap<String, Any>()
        content["title"] = "Olá Fulano!"

        return listOf(
            WidgetDto(WidgetIdentifier.HOME_HEADER_WIDGET, content),
            WidgetDto(null, content)
        )
    }
}

