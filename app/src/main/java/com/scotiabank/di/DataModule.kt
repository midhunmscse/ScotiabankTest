package com.scotiabank.di

import android.content.Context
import com.scotiabank.data.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataModule {

    /**
     * Dagger Hilt module that provides dependencies related to data and network operations.
     * This module is installed in the [SingletonComponent] scope.
     */

    /**
     * Provides an instance of the [ApiService] for making network API calls.
     *
     * @param appContext The application context provided by Dagger Hilt.
     * @return An instance of [ApiService].
     */
    @Singleton
    @Provides
    fun provideNowApiService(@ApplicationContext appContext: Context): ApiService {
        return ApiService()
    }
}
