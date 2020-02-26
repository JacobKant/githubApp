package ru.jacobkant.githubapp.di

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.*

interface ViewModelFactoryProvider {
    fun viewModelFactory(): ViewModelProvider.Factory
}

inline fun <reified VM : ViewModel> Fragment.getViewModel(
    provider: ViewModelFactoryProvider,
    initOnCreate: Boolean = true
): Lazy<VM> {
    val lazy = lazy { ViewModelProvider(this, provider.viewModelFactory()).get(VM::class.java) }
    if (initOnCreate) {
        lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                when (event) {
                    Lifecycle.Event.ON_CREATE -> {
                        lazy.value // initialize
                    }
                    Lifecycle.Event.ON_DESTROY -> {
                        lifecycle.removeObserver(this)
                    }
                    else -> {}
                }
            }
        })
    }
    return lazy
}


inline fun <reified VM : ViewModel> AppCompatActivity.getViewModel(
    provider: ViewModelFactoryProvider,
    initOnCreate: Boolean = true
): Lazy<VM> {
    val lazy = lazy { ViewModelProvider(this, provider.viewModelFactory()).get(VM::class.java) }
    if (initOnCreate) {
        lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                when (event) {
                    Lifecycle.Event.ON_CREATE -> {
                        lazy.value // initialize
                    }
                    Lifecycle.Event.ON_DESTROY -> {
                        lifecycle.removeObserver(this)
                    }
                    else -> {}
                }
            }
        })
    }
    return lazy
}
