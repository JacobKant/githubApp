package ru.jacobkant.githubapp.utils

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import ru.jacobkant.githubapp.utils.SchedulersProvider

class SchedulersProviderForTest :
    SchedulersProvider {
    override fun ui(): Scheduler =
        Schedulers.trampoline()

    override fun io(): Scheduler =
        Schedulers.trampoline()

    override fun computation(): Scheduler =
        Schedulers.trampoline()

    override fun newThread(): Scheduler =
        Schedulers.trampoline()

    override fun tramposine(): Scheduler =
        Schedulers.trampoline()

}