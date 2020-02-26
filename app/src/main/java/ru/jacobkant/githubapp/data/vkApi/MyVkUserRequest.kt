package ru.jacobkant.githubapp.data.vkApi

import com.vk.api.sdk.requests.VKRequest
import org.json.JSONObject
import ru.jacobkant.githubapp.domain.MyProfileInfo

class MyVkUserRequest : VKRequest<MyProfileInfo>("users.get") {
    init {
        addParam("fields", "photo_200, screen_name")
    }

    override fun parse(r: JSONObject): MyProfileInfo {
        val firstUser = r.getJSONArray("response").getJSONObject(0)
        return MyProfileInfo(
            firstName = firstUser.getString("first_name"),
            lastName = firstUser.getString("last_name"),
            loginName = firstUser.getString("screen_name"),
            photoUrl = firstUser.getString("photo_200")
        )
    }
}