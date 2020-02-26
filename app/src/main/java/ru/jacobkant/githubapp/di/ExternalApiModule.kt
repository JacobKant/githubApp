package ru.jacobkant.githubapp.di

import dagger.Binds
import dagger.Module
import ru.jacobkant.githubapp.data.githubApi.graphql.GithubApi
import ru.jacobkant.githubapp.data.githubApi.graphql.GithubApiV4
import ru.jacobkant.githubapp.data.preferences.DefaultPreferences
import ru.jacobkant.githubapp.data.preferences.DefaultPreferencesImpl
import ru.jacobkant.githubapp.data.vkApi.VkApi
import ru.jacobkant.githubapp.data.vkApi.VkApiImpl
import javax.inject.Singleton

@Module
abstract class ExternalApiModule {
    @Binds
    @Singleton
    abstract fun vkApi(vkApi: VkApiImpl): VkApi

    @Binds
    @Singleton
    abstract fun githubApi(githubApi: GithubApiV4): GithubApi

    @Binds
    @Singleton
    abstract fun defaultPreferences(githubApi: DefaultPreferencesImpl): DefaultPreferences
}