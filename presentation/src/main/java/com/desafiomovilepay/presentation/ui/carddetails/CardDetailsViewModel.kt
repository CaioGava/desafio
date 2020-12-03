package com.desafiomovilepay.presentation.ui.carddetails

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.desafiomovilepay.presentation.mapper.CardDetailsMapper
import com.desafiomovilepay.presentation.model.CardDetails
import com.desafiomovilepay.repository.Response
import com.desafiomovilepay.repository.domain.CardChecker
import com.desafiomovilepay.repository.dto.CardDetailsDto
import com.desafiomovilepay.repository.gateway.card.ICardRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CardDetailsViewModel
@ViewModelInject constructor(
    private val cardRepository: ICardRepository,
    private val cardChecker: CardChecker,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = MutableStateFlow<State>(State.Empty)
    val state: StateFlow<State> get() = _state

    sealed class State {
        object Loading : State()
        data class Success(val cardDetails: CardDetails) : State()
        data class Error(val msg: String) : State()
        object Empty : State()
    }

    fun requestCardDetails(cardId: String) {
        if (!cardChecker.isCardIdMarkedAsAllowed(cardId)) {
            _state.value = State.Error("CardId must be 123")
            return
        }

        viewModelScope.launch {
            _state.value = State.Loading

            cardRepository.getCardDetails(cardId).collect {
                when (it) {
                    is Response.Success ->
                        _state.value =
                            State.Success(CardDetailsMapper.mapFromDto(it.dto as CardDetailsDto))
                    is Response.Error ->
                        _state.value = State.Error("Ooops! Something went wrong.")
                }
            }
        }
    }
}