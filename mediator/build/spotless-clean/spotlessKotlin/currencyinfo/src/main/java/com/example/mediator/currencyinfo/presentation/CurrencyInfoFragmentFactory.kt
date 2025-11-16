package com.example.mediator.currencyinfo.presentation

import androidx.fragment.app.Fragment

interface CurrencyInfoFragmentFactory {

    val tag: String

    fun createFragment(groupId: Int? = null): Fragment
}
