package ru.jacobkant.githubapp.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import ru.jacobkant.githubapp.domain.AuthRepository
import ru.jacobkant.githubapp.domain.MyProfileInfo
import ru.jacobkant.githubapp.utils.SchedulersProvider
import ru.jacobkant.githubapp.utils.addTo
import javax.inject.Inject

class MenuFragmentContainerViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    schedulersProvider: SchedulersProvider
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    val myProfileInfo: MutableLiveData<MyProfileInfo> = MutableLiveData()

    init {
        authRepository.getCurrentUser()
            .observeOn(schedulersProvider.ui())
            .subscribe(
                {
                    myProfileInfo.setValue(it)
                },
                {
                    // Todo show toast and retry on connectivity changes
                })
            .addTo(compositeDisposable)
    }


    fun clickLogout() {
        authRepository.logOut()
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}