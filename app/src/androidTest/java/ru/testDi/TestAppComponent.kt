package ru.testDi

import dagger.Component
import ru.jacobkant.githubapp.di.*
import ru.jacobkant.githubapp.ui.main.MainActivityTest
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, FakeExternalApiModule::class, ViewModelModule::class, NavigationModule::class, RepositoryModule::class])
interface TestAppComponent : AppComponent {
    fun inject(test: MainActivityTest)
}