package com.scotiabank.di

import com.scotiabank.data.network.source.UserDataSource
import com.scotiabank.data.network.source.UserDataSourceImpl
import com.scotiabank.data.repository.UserRepository
import com.scotiabank.data.repository.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
annotation class UsersRepository

/**
 * Dagger Hilt module that provides bindings for the user-related dependencies.
 * This module is installed in the [SingletonComponent] scope.
 */
@InstallIn(SingletonComponent::class)
@Module
abstract class UserModule {

    /**
     * Binds the implementation of [UserRepositoryImpl] to the [UserRepository] interface.
     * This ensures that instances of [UserRepositoryImpl] can be injected wherever [UserRepository] is required.
     *
     * @param impl The implementation of [UserRepositoryImpl] to bind.
     * @return An instance of [UserRepository] bound to [UserRepositoryImpl].
     */
    @Binds
    @Singleton
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository

    /**
     * Binds the implementation of [UserDataSourceImpl] to the [UserDataSource] interface.
     * This ensures that instances of [UserDataSourceImpl] can be injected wherever [UserDataSource] is required.
     *
     * @param impl The implementation of [UserDataSourceImpl] to bind.
     * @return An instance of [UserDataSource] bound to [UserDataSourceImpl].
     */
    @Binds
    @Singleton
    abstract fun bindUserDataSource(impl: UserDataSourceImpl): UserDataSource
}
