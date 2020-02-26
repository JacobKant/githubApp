package ru.jacobkant.githubapp.utils

import io.reactivex.Scheduler

interface SchedulersProvider{
    fun ui(): Scheduler
    fun io(): Scheduler
    fun computation(): Scheduler
    fun newThread(): Scheduler
    fun tramposine(): Scheduler
}