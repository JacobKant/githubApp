package ru.jacobkant.githubapp.ui.main.users

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import ru.jacobkant.githubapp.data.PagingResponse

enum class StateStatus {
    Empty, EmptyProgress, EmptyError, Data, Refreshing, NewPageProgress, FullDataLoaded
}

data class PaginatorState<InDataType>(
    val status: StateStatus = StateStatus.Empty,
    val data: List<InDataType> = listOf(),
    val totalItemsCount: Int = 0,
    val loadedItemsCount: Int = 0,
    val lastError: Throwable? = null
)

class CursorPaginator<QueryArg, InT>(
    private val pageRequest: (QueryArg?, pageSize: Int, afterCursor: String?) -> Single<PagingResponse<InT>>,
    private var currentQueryRequestArg: QueryArg? = null
) {
    private val defaultPageSize: Int = 30
    private var lastItemCursor: String? = null
    private val compositeDisposable = CompositeDisposable()
    private val state: BehaviorSubject<PaginatorState<InT>> =
        BehaviorSubject.createDefault(PaginatorState())

    val queryArg: QueryArg?
        get() = currentQueryRequestArg

    private val currentState
        get() = state.value!!

    fun getListUpdates(): Observable<PaginatorState<InT>> {
        return state.doOnDispose { compositeDisposable.clear() }
    }

    fun refresh(queryArg1: QueryArg? = currentQueryRequestArg) {
        lastItemCursor = null
        if (queryArg1 == null || (queryArg1 is String && queryArg1.isEmpty())) {
            state.onNext(
                currentState.copy(
                    status = StateStatus.Empty,
                    data = listOf(),
                    totalItemsCount = 0,
                    loadedItemsCount = 0,
                    lastError = null
                )
            )
        } else requestPage(queryArg1, true)
    }

    fun loadMore() {
        if (currentState.status == StateStatus.NewPageProgress) return
        requestPage(currentQueryRequestArg, false)
    }

    private fun requestPage(
        qArg: QueryArg?,
        isRefresh: Boolean
    ) {
        compositeDisposable.clear()
        this.currentQueryRequestArg = qArg
        compositeDisposable.add(
            pageRequest(qArg, defaultPageSize, lastItemCursor)
                .doOnSubscribe {
                    state.onNext(
                        currentState.copy(
                            status = when {
                                currentState.data.isEmpty() -> StateStatus.EmptyProgress
                                isRefresh -> StateStatus.Refreshing
                                else -> StateStatus.NewPageProgress
                            }
                        )
                    )
                }
                .subscribe({
                    lastItemCursor = it.lastCursor
                    val newItems = if (isRefresh) it.items else currentState.data + it.items
                    val isFullDataLoaded = it.totalItemsCount == newItems.size
                    state.onNext(
                        currentState.copy(
                            status = if (isFullDataLoaded) StateStatus.FullDataLoaded else StateStatus.Data,
                            totalItemsCount = it.totalItemsCount,
                            loadedItemsCount = newItems.size,
                            data = newItems,
                            lastError = null
                        )
                    )
                }, {
                    state.onNext(
                        currentState.copy(
                            status = if (currentState.data.isNotEmpty()) StateStatus.Data else StateStatus.EmptyError,
                            lastError = it
                        )
                    )
                })
        )
    }


}