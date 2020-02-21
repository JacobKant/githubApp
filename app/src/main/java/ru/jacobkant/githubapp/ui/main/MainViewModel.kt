package ru.jacobkant.githubapp.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import ru.jacobkant.githubapp.data.AuthRepositoryImpl
import ru.jacobkant.githubapp.data.MyVkUser
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val authRepository: AuthRepositoryImpl
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val myVkUser: MutableLiveData<MyVkUser> = MutableLiveData()

    init {
        authRepository.getCurrentUser()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    myVkUser.setValue(it)
                },
                {})
            .addTo(compositeDisposable)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}
