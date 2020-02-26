package ru.jacobkant.githubapp.di

import dagger.Component
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ExternalApiModule::class, ViewModelModule::class, NavigationModule::class, RepositoryModule::class])
interface AppComponent : ViewModelFactoryProvider {
    fun provideRootCicerone(): Cicerone<Router>
}


