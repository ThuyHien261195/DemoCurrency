package com.example.democurrency.presentation

sealed class MainAction {

    data class ShowListAction(val groupId: Int) : MainAction()

    data object ShowAllAction: MainAction()
}