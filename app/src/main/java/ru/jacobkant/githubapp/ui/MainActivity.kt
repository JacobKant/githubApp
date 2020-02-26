package ru.jacobkant.githubapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.jacobkant.githubapp.App
import ru.jacobkant.githubapp.R
import ru.jacobkant.githubapp.data.androidUtils.ActivityResult
import ru.jacobkant.githubapp.data.androidUtils.ActivityResultChannel
import ru.jacobkant.githubapp.di.getViewModel
import ru.jacobkant.githubapp.domain.AuthRepository
import ru.jacobkant.githubapp.ui.login.LoginViewModel
import ru.jacobkant.githubapp.ui.main.MainActivityViewModel
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import javax.inject.Inject

class MainActivity : AppCompatActivity(R.layout.main_activity) {

    private val viewModel: MainActivityViewModel by getViewModel(App.components.appComponent)

    override fun onResumeFragments() {
        super.onResumeFragments()
        viewModel.rootCicerone.navigatorHolder
            .setNavigator(SupportAppNavigator(this, R.id.container))
    }

    override fun onPause() {
        viewModel.rootCicerone.navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        viewModel.resultChannel.dispatch(ActivityResult(requestCode, resultCode, data))
        super.onActivityResult(requestCode, resultCode, data)
    }
}


