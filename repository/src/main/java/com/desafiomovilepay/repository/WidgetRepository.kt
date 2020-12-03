package com.desafiomovilepay.repository

import com.desafiomovilepay.repository.di.qualifiers.IoDispatcher
import com.desafiomovilepay.repository.domain.WidgetChecker
import com.desafiomovilepay.repository.gateway.widget.WidgetApiGateway
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface IWidgetRepository {
    suspend fun getWidgets(): Flow<Response>
}

class WidgetRepository
@Inject constructor(
    private val gateway: WidgetApiGateway,
    private val widgetChecker: WidgetChecker,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) : IWidgetRepository {

    override suspend fun getWidgets(): Flow<Response> {
        return flow<Response> {
            val widgets = gateway.getWidgets().widgets
            val filtered = widgetChecker.filterForAllowedWidgets(widgets)
            emit(Response.Success(filtered))
        }.catch { throwable ->
            emit(Response.Error(throwable))
        }.flowOn(dispatcher)
    }
}