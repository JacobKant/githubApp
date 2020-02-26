package ru.jacobkant.githubapp.di

import dagger.Binds
import dagger.Module
import ru.jacobkant.githubapp.data.AuthRepositoryImpl
import ru.jacobkant.githubapp.domain.AuthRepository
import javax.inject.Singleton

@Module
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindAuthRepo(authRepositoryImpl: AuthRepositoryImpl): AuthRepository
}