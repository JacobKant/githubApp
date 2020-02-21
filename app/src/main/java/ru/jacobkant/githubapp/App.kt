package ru.jacobkant.githubapp

import android.app.Application
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKTokenExpiredHandler
import ru.jacobkant.githubapp.di.AppModule
import ru.jacobkant.githubapp.di.Components
import ru.jacobkant.githubapp.di.DaggerAppComponent

class App : Application() {

    companion object {
        lateinit var components: Components
    }

    override fun onCreate() {
        super.onCreate()
        components = Components(
            DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
        )

        VK.addTokenExpiredHandler(object : VKTokenExpiredHandler {
            override fun onTokenExpired() {
                // token expired
            }
        })
    }

}

