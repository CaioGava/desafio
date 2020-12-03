package com.desafiomovilepay.repository.gateway.statement

import com.desafiomovilepay.repository.dto.StatementDetailsDto
import retrofit2.http.GET
import retrofit2.http.Path

interface StatementApiGateway {
    @GET("statement/{id}")
    suspend fun getStatementDetails(@Path("id") id: String): StatementDetailsDto
}