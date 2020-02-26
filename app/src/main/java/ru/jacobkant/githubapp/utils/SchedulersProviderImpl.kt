package ru.jacobkant.githubapp.utils

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.jacobkant.githubapp.utils.SchedulersProvider

class SchedulersProviderImpl :
    SchedulersProvider {
    override fun ui(): Scheduler = AndroidSchedulers.mainThread()

    override fun io(): Scheduler = Schedulers.io()

    override fun computation(): Scheduler = Schedulers.computation()

    override fun newThread(): Scheduler = Schedulers.newThread()

    override fun tramposine(): Scheduler = Schedulers.trampoline()
}

