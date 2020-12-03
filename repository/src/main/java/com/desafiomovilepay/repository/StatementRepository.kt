package com.desafiomovilepay.repository.gateway.statement

import com.desafiomovilepay.repository.Response
import com.desafiomovilepay.repository.di.qualifiers.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface IStatementRepository {
    suspend fun getStatementDetails(accountId: String): Flow<Response>
}

class StatementRepository
@Inject constructor(
    private var gateway: StatementApiGateway,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : IStatementRepository {

    override suspend fun getStatementDetails(accountId: String): Flow<Response> {
        return flow<Response> {
            val cardDetails = gateway.getStatementDetails(accountId)
            emit(Response.Success(cardDetails))
        }.catch { throwable ->
            println("Something went wrong:" + throwable.stackTrace)
            emit(Response.Error(throwable))
        }.flowOn(dispatcher)
    }
}