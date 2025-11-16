package com.example.democurrency.presentation.ui

sealed class StateEvent {

    object ClearSuccess : StateEvent()
    object InitSuccess : StateEvent()
    object AddSuccess : StateEvent()
    object Loading : StateEvent()
    data class Error(val message: String) : StateEvent()
}
