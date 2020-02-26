package ru.jacobkant.githubapp.data.vkApi

import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAuthCallback
import io.reactivex.Single
import ru.jacobkant.githubapp.data.androidUtils.ActivityResult
import ru.jacobkant.githubapp.domain.MyProfileInfo
import javax.inject.Inject

class VkApiImpl @Inject constructor() : VkApi {
    override fun requestMyUser(): Single<MyProfileInfo> {
        return Single.fromCallable {
            VK.executeSync(
                MyVkUserRequest()
            )
        }
    }

    override fun logOut() {
        VK.logout()
    }

    override fun receiveVkLoginResult(result: ActivityResult, callback: VKAuthCallback) {
        VK.onActivityResult(
            result.requestCode,
            result.resultCode,
            result.data,
            callback
        )
    }
}