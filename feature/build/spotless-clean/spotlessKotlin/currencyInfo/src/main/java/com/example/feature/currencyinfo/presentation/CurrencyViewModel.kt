package com.example.feature.currencyinfo.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feature.currencyinfo.presentation.CurrencyInfoFragment.Companion.ARG_GROUP_ID
import com.example.mediator.currencyinfo.domain.CurrencyRepository
import com.example.mediator.currencyinfo.presentation.mapper.toCurrencyGroupUiModel
import com.example.mediator.currencyinfo.presentation.mapper.toCurrencyInfoUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
@HiltViewModel
class CurrencyViewModel @Inject constructor(
    private val repository: CurrencyRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val groupId = savedStateHandle.get<Int>(ARG_GROUP_ID) ?: null

    private val _searchKeywordFlow = MutableStateFlow("")
    val searchKeywordFlow = _searchKeywordFlow.asStateFlow()

    val groupInfoFlow = repository.getGroupInfo(groupId).map {
        it?.toCurrencyGroupUiModel()
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        null,
    )

    val currenciesFlow = searchKeywordFlow
//            .distinctUntilChanged()
        .flatMapLatest { keyword ->
            if (keyword.isEmpty()) {
                repository.getCurrenciesByGroup(groupId)
            } else {
                repository.searchCurrencies(keyword, groupId)
            }.map { response ->
                response.map { item ->
                    item.toCurrencyInfoUiModel()
                }.toImmutableList()
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            null,
        )

    fun searchCurrencies(keyword: String) {
        _searchKeywordFlow.value = keyword
    }

    fun clearSearch() {
        _searchKeywordFlow.value = ""
    }

    companion object {
        private const val DEBOUNCE_SEARCH_TIME = 300L
    }
}
