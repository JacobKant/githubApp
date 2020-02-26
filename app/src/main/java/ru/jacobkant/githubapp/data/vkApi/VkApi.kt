package ru.jacobkant.githubapp.data.vkApi

import com.vk.api.sdk.auth.VKAuthCallback
import io.reactivex.Single
import ru.jacobkant.githubapp.data.androidUtils.ActivityResult
import ru.jacobkant.githubapp.domain.MyProfileInfo

interface VkApi {
    fun requestMyUser(): Single<MyProfileInfo>
    fun logOut()
    fun receiveVkLoginResult(result: ActivityResult, callback: VKAuthCallback)
}