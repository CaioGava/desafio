package com.desafiomovilepay.repository.gateway.card

import com.desafiomovilepay.repository.dto.CardDetailsDto
import retrofit2.http.GET
import retrofit2.http.Path

interface CardApiGateway {
    @GET("card/{id}")
    suspend fun getCardDetails(@Path("id") id: String): CardDetailsDto
}