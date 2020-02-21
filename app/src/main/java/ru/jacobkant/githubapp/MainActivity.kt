package ru.jacobkant.githubapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import ru.jacobkant.githubapp.ui.main.MainFragment
import ru.jacobkant.githubapp.ui.users.UsersFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitAllowingStateLoss()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val callback = object : VKAuthCallback {
            override fun onLogin(token: VKAccessToken) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, UsersFragment())
                    .commitAllowingStateLoss()
            }

            override fun onLoginFailed(errorCode: Int) {
                Toast.makeText(this@MainActivity, "Ошибка $errorCode", Toast.LENGTH_LONG).show()
            }
        }

        if (data == null || !VK.onActivityResult(requestCode, resultCode, data, callback)) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}


