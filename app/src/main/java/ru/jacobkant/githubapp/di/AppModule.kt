package ru.jacobkant.githubapp.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.jacobkant.githubapp.utils.SchedulersProvider
import ru.jacobkant.githubapp.utils.SchedulersProviderImpl
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {

    @Provides
    @Singleton
    fun appContext(): Context {
        return context
    }

    @Provides
    @Singleton
    fun schedulersProvider(): SchedulersProvider {
        return SchedulersProviderImpl()
    }

}