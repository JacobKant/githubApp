package ru.jacobkant.githubapp.data.vkApi

import com.vk.api.sdk.requests.VKRequest
import org.json.JSONObject
import ru.jacobkant.githubapp.data.MyVkUser

class MyVkUserRequest : VKRequest<MyVkUser>("users.get") {
    init {
        addParam("fields", "photo_200, screen_name")
    }

    override fun parse(r: JSONObject): MyVkUser {
        val firstUser = r.getJSONArray("response").getJSONObject(0)
        return MyVkUser(
            firstName = firstUser.getString("first_name"),
            lastName = firstUser.getString("last_name"),
            loginName = firstUser.getString("screen_name"),
            photoUrl = firstUser.getString("photo_200")
        )
    }
}