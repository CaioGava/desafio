package com.desafiomovilepay.repository.gateway.card

import com.desafiomovilepay.repository.Response
import com.desafiomovilepay.repository.di.qualifiers.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface ICardRepository {
    suspend fun getCardDetails(cardId: String): Flow<Response>
}

class CardRepository
@Inject constructor(
    private var gateway: CardApiGateway,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ICardRepository {

    override suspend fun getCardDetails(cardId: String): Flow<Response> {
        return flow<Response> {
            val cardDetails = gateway.getCardDetails(cardId)
            emit(Response.Success(cardDetails))
        }.catch { throwable ->
            println("Something went wrong:" + throwable.stackTrace)
            emit(Response.Error(throwable))
        }.flowOn(dispatcher)
    }
}
