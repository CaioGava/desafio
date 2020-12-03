package com.desafiomovilepay.presentation.ui.homescreen

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.desafiomovilepay.repository.IWidgetRepository
import com.desafiomovilepay.repository.Response
import com.desafiomovilepay.repository.dto.WidgetDto
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeScreenViewModel
@ViewModelInject constructor(
    private val widgetRepository: IWidgetRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _allWidgets = MutableLiveData<List<WidgetDto>>(mutableListOf())
    val allWidgets: LiveData<List<WidgetDto>> get() = _allWidgets

    fun getWidgets() {
        viewModelScope.launch {
            widgetRepository.getWidgets().collect { response ->
                when (response) {
                    is Response.Success ->
                        _allWidgets.value = response.dto as List<WidgetDto>
                    is Response.Error ->
                        _allWidgets.value = listOf()
                }
            }
        }
    }
}