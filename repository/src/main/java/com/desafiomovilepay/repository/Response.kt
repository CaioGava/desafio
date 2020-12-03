package com.desafiomovilepay.repository

sealed class Response {
    data class Success(val dto: Any) : Response()
    data class Error(val error: Throwable) : Response()
}