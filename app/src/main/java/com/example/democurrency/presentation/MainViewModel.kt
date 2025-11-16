package com.example.democurrency.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.democurrency.presentation.ui.StateEvent
import com.example.mediator.currencyinfo.domain.CurrencyRepository
import com.example.mediator.currencyinfo.presentation.mapper.toCurrencyGroupUiModel
import com.example.mediator.currencyinfo.presentation.mapper.toCurrencyInfoModel
import com.example.mediator.currencyinfo.presentation.uimodel.CurrencyGroupUiModel
import com.example.mediator.currencyinfo.presentation.uimodel.CurrencyInfoUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: CurrencyRepository,
) : ViewModel() {

    private val _uiActionFlow = MutableSharedFlow<MainAction>()
    val uiActionFlow = _uiActionFlow.asSharedFlow()

    private val _stateEventFlow = MutableSharedFlow<StateEvent>()
    val stateEventFlow = _stateEventFlow.asSharedFlow()

    init {
        initDb()
    }

    val groupsFlow: StateFlow<ImmutableList<CurrencyGroupUiModel>> =
        repository.getAllGroupInfos()
            .map {
                it.map { group ->
                    group.toCurrencyGroupUiModel()
                }.toImmutableList()
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Lazily,
                initialValue = persistentListOf(),
            )

    fun initDb() {
        viewModelScope.launch {
            try {
                _stateEventFlow.emit(StateEvent.Loading)
                repository.initData()
                _stateEventFlow.emit(StateEvent.InitSuccess)
            } catch (e: Exception) {
                _stateEventFlow.emit(StateEvent.Error(e.message ?: ""))
            }
        }
    }

    fun clearDb() {
        viewModelScope.launch {
            try {
                _stateEventFlow.emit(StateEvent.Loading)
                repository.clearAllDb()
                _stateEventFlow.emit(StateEvent.ClearSuccess)
            } catch (e: Exception) {
                _stateEventFlow.emit(StateEvent.Error(e.message ?: ""))
            }
        }
    }

    fun addCurrencyInfo(
        groupId: Int,
        currencyInfoUiModel: CurrencyInfoUiModel
    ) {
        viewModelScope.launch {
            try {
                _stateEventFlow.emit(StateEvent.Loading)
                repository.insertCurrency(currencyInfoUiModel.toCurrencyInfoModel(), groupId)
                _stateEventFlow.emit(StateEvent.AddSuccess)
            } catch (e: Exception) {
                _stateEventFlow.emit(StateEvent.Error(e.message ?: ""))
            }
        }
    }

    fun navigateAction(action: MainAction) {
        viewModelScope.launch {
            _uiActionFlow.emit(action)
        }
    }
}