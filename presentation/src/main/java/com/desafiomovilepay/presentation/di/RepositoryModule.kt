package com.desafiomovilepay.presentation.di

import com.desafiomovilepay.repository.IWidgetRepository
import com.desafiomovilepay.repository.WidgetRepository
import com.desafiomovilepay.repository.gateway.card.CardRepository
import com.desafiomovilepay.repository.gateway.card.ICardRepository
import com.desafiomovilepay.repository.gateway.statement.IStatementRepository
import com.desafiomovilepay.repository.gateway.statement.StatementRepository


import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun provideWidgetRepository(widgetRepository: WidgetRepository): IWidgetRepository

    @Singleton
    @Binds
    abstract fun provideCardRepository(cardRepository: CardRepository): ICardRepository

    @Singleton
    @Binds
    abstract fun provideStatementRepository(statementRepository: StatementRepository): IStatementRepository
}