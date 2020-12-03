package com.desafiomovilepay.repository.gateway.widget

import com.desafiomovilepay.repository.dto.WidgetsDto
import retrofit2.http.GET

interface WidgetApiGateway {

    @GET("home")
    suspend fun getWidgets(): WidgetsDto
}