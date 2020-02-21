package ru.jacobkant.githubapp.data.vkApi

import com.vk.api.sdk.VK
import io.reactivex.Single
import ru.jacobkant.githubapp.data.MyVkUser
import javax.inject.Inject

class VkApiImpl @Inject constructor() :
    VkApi {
    override fun requestMyUser(): Single<MyVkUser> {
        return Single.fromCallable {
            VK.executeSync(
                MyVkUserRequest()
            )
        }
    }
}