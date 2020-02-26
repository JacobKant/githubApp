package ru.jacobkant.githubapp

import android.app.Application
import androidx.annotation.VisibleForTesting
import com.facebook.stetho.Stetho
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKTokenExpiredHandler
import ru.jacobkant.githubapp.di.AppComponent
import ru.jacobkant.githubapp.di.AppModule
import ru.jacobkant.githubapp.di.Components
import ru.jacobkant.githubapp.di.DaggerAppComponent
import ru.jacobkant.githubapp.ui.common.Screens

class App : Application() {

    companion object {
        lateinit var components: Components
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }

        components = Components(
            DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
        )

        VK.addTokenExpiredHandler(object : VKTokenExpiredHandler {
            override fun onTokenExpired() {
                components.appComponent.provideRootCicerone()
                    .router.newRootScreen(Screens.LoginScreen())
            }
        })
    }

    @VisibleForTesting
    fun setAppComponent(component: AppComponent) {
        components.appComponent = component
    }

}

