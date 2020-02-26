package ru.jacobkant.githubapp.ui.common

import androidx.fragment.app.Fragment
import ru.jacobkant.githubapp.ui.main.MenuContainerFragment
import ru.jacobkant.githubapp.ui.login.LoginFragment
import ru.jacobkant.githubapp.ui.main.users.UsersFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Screens {

    class LoginScreen: SupportAppScreen() {
        override fun getFragment(): Fragment? {
            return LoginFragment()
        }
    }

    class MainScreen : SupportAppScreen() {
        override fun getFragment(): Fragment? {
            return MenuContainerFragment()
        }
    }

    class UsersScreen : SupportAppScreen() {
        override fun getFragment(): Fragment? {
            return UsersFragment()
        }
    }

}
