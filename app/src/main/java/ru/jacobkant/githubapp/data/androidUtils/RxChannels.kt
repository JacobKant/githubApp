package ru.jacobkant.githubapp.data.androidUtils

import android.content.Intent
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject
import javax.inject.Singleton

abstract class EventsChannel<T> {
    private val events: PublishSubject<T> = PublishSubject.create()

    fun observable(): Observable<T> {
        return events.hide()
    }

    fun dispatch(vararg event: T) {
        event.forEach(events::onNext)
    }
}

class ActivityResult(val requestCode: Int, val resultCode: Int, val data: Intent?)

@Singleton
class ActivityResultChannel @Inject constructor() : EventsChannel<ActivityResult>()
