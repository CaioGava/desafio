package com.desafiomovilepay.repository.di

import com.desafiomovilepay.repository.di.qualifiers.DefaultDispatcher
import com.desafiomovilepay.repository.di.qualifiers.IoDispatcher
import com.desafiomovilepay.repository.di.qualifiers.MainDispatcher
import com.desafiomovilepay.repository.gateway.card.CardApiGateway
import com.desafiomovilepay.repository.gateway.statement.StatementApiGateway
import com.desafiomovilepay.repository.gateway.widget.WidgetApiGateway
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class RetrofitModule {

    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder().create()
    }

    @Singleton
    @Provides
    fun provideRetrofitBuilder(gson: Gson): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl("https://mpay-android-interview.herokuapp.com/android/interview/")
            .addConverterFactory(GsonConverterFactory.create(gson))
    }

    @Singleton
    @Provides
    fun provideWidgetApiGateway(builder: Retrofit.Builder): WidgetApiGateway {
        return builder.build().create(WidgetApiGateway::class.java)
    }

    @Singleton
    @Provides
    fun provideCardApiGateway(builder: Retrofit.Builder): CardApiGateway {
        return builder.build().create(CardApiGateway::class.java)
    }

    @Singleton
    @Provides
    fun provideStatementApiGateway(builder: Retrofit.Builder): StatementApiGateway {
        return builder.build().create(StatementApiGateway::class.java)
    }

    @IoDispatcher
    @Provides
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @DefaultDispatcher
    @Provides
    fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @MainDispatcher
    @Provides
    fun providesMainDispatcher(): CoroutineDispatcher = Dispatchers.Main
}
