package ru.jacobkant.githubapp.di

import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router
import javax.inject.Singleton


@Module
class NavigationModule {
    private val rootCicerone = Cicerone.create()

    @Provides
    @Singleton
    fun provideRootCicerone(): Cicerone<Router> {
        return rootCicerone
    }

    @Provides
    @Singleton
    fun provideRootRouter(): Router {
        return rootCicerone.router
    }
}