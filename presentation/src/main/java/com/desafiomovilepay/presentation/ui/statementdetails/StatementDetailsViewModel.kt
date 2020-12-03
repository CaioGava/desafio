package com.desafiomovilepay.presentation.ui.statementdetails

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.desafiomovilepay.presentation.mapper.StatementDetailsMapper
import com.desafiomovilepay.presentation.model.StatementDetails
import com.desafiomovilepay.repository.Response
import com.desafiomovilepay.repository.domain.AccountChecker
import com.desafiomovilepay.repository.dto.StatementDetailsDto
import com.desafiomovilepay.repository.gateway.statement.IStatementRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class StatementDetailsViewModel @ViewModelInject constructor(
    private val statementRepository: IStatementRepository,
    private val accountChecker: AccountChecker,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = MutableStateFlow<State>(State.Empty)
    val state: StateFlow<State> get() = _state

    sealed class State {
        object Loading : State()
        data class Success(val details: StatementDetails) : State()
        data class Error(val msg: String) : State()
        object Empty : State()
    }

    fun requestStatementDetails(accountId: String) {
        if (!accountChecker.isAccountIdMarkedAsAllowed(accountId)) {
            _state.value = State.Error("AccountId must be 123")
            return
        }

        viewModelScope.launch {
            _state.value = State.Loading

            statementRepository.getStatementDetails(accountId).collect {
                when (it) {
                    is Response.Success ->
                        _state.value = State.Success(
                            StatementDetailsMapper.mapFromDto(it.dto as StatementDetailsDto)
                        )
                    is Response.Error ->
                        _state.value = State.Error("Ooops! Something went wrong.")
                }
            }
        }
    }
}