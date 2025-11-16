package com.example.feature.currencyinfo.di

import com.example.feature.currencyinfo.data.CurrencyRepositoryImpl
import com.example.feature.currencyinfo.presentation.CurrencyInfoFragmentFactoryImpl
import com.example.mediator.currencyinfo.domain.CurrencyRepository
import com.example.mediator.currencyinfo.presentation.CurrencyInfoFragmentFactory
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CurrencyInfoModule {

    @Binds
    abstract fun bindCurrencyInfoFragmentFactory(
        impl: CurrencyInfoFragmentFactoryImpl,
    ): CurrencyInfoFragmentFactory

    @Binds
    abstract fun bindCurrencyRepository(
        impl: CurrencyRepositoryImpl,
    ): CurrencyRepository
}
