package ru.jacobkant.githubapp.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.jacobkant.githubapp.ui.main.MenuFragmentContainerViewModel
import ru.jacobkant.githubapp.ui.login.LoginViewModel
import ru.jacobkant.githubapp.ui.main.MainActivityViewModel
import ru.jacobkant.githubapp.ui.main.users.UsersViewModel
import javax.inject.Singleton

@Module
abstract class ViewModelModule {

    @IntoMap
    @Binds
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindMainViewModel(vm: LoginViewModel): ViewModel

    @IntoMap
    @Binds
    @ViewModelKey(UsersViewModel::class)
    abstract fun bindUsersViewModel(factory: UsersViewModel): ViewModel

    @IntoMap
    @Binds
    @ViewModelKey(MenuFragmentContainerViewModel::class)
    abstract fun bindMenuFragmentContainerViewModel(vm: MenuFragmentContainerViewModel): ViewModel

    @IntoMap
    @Binds
    @ViewModelKey(MainActivityViewModel::class)
    abstract fun bindMainActivityViewModel(vm: MainActivityViewModel): ViewModel


    @Binds
    @Singleton
    abstract fun viewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

}