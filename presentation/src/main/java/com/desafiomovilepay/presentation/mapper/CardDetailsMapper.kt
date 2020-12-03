package com.desafiomovilepay.presentation.mapper

import com.desafiomovilepay.presentation.model.CardDetails
import com.desafiomovilepay.repository.dto.CardDetailsDto

object CardDetailsMapper : Mapper<CardDetailsDto, CardDetails> {
    override fun mapFromDto(dto: CardDetailsDto): CardDetails {
        return CardDetails(
            dto.cardNumber,
            dto.cardName,
            dto.expirationDate,
            dto.availableLimit,
            dto.totalLimit,
        )
    }
}

