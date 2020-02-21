package ru.jacobkant.githubapp.di

import android.content.Context
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides
import ru.jacobkant.githubapp.data.vkApi.VkApi
import ru.jacobkant.githubapp.data.vkApi.VkApiImpl
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ViewModelModule::class])
interface AppComponent : ComponentWithViewModels


@Module(includes = [AppBindModule::class])
class AppModule(private val context: Context) {

    @Provides
    @Singleton
    fun appContext(): Context {
        return context
    }

}

@Module
abstract class AppBindModule {
    @Binds
    @Singleton
    abstract fun vkApi(vkApi: VkApiImpl): VkApi
}


