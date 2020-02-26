package ru.jacobkant.githubapp.ui.main.users

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.UsersQuery
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import ru.jacobkant.githubapp.data.GhUsersRepository
import ru.jacobkant.githubapp.utils.addTo
import javax.inject.Inject

class UsersViewModel @Inject constructor(private val githubRepo: GhUsersRepository) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    val paginatorLiveData = MutableLiveData<PaginatorState<UsersQuery.AsUser>>()

    private val paginator = CursorPaginator<String, UsersQuery.AsUser>(
        pageRequest = { queryReq: String?, pageSize: Int, cursor: String? ->
            githubRepo.getUsers(queryReq ?: "", pageSize, cursor).firstOrError()
        })

    init {
        paginator.getListUpdates()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { paginatorLiveData.value = it }
            .addTo(compositeDisposable)
    }

    fun onChangeQuery(newText: String?) {
        paginator.refresh(newText)
    }

    fun onLoadMore() {
        paginator.loadMore()
    }

    fun onSwipeRefresh() {
        paginator.refresh(paginator.queryArg)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}