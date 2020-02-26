package ru.jacobkant.githubapp.data

import com.apollographql.apollo.api.Response
import com.github.UsersQuery
import io.reactivex.Observable
import ru.jacobkant.githubapp.data.githubApi.graphql.GithubApi
import javax.inject.Inject

data class PagingResponse<T>(val items: List<T>, val totalItemsCount: Int, val lastCursor: String?)

class GhUsersRepository @Inject constructor(private val ghApi: GithubApi) {
    fun getUsers(
        query: String,
        pageSize: Int,
        afterCursor: String?
    ): Observable<PagingResponse<UsersQuery.AsUser>> {
        return ghApi.queryUsers(query, pageSize, afterCursor)
            .flatMap { res: Response<UsersQuery.Data> ->
                val users = res.data()?.search?.edges?.mapNotNull { it?.node?.asUser }
                val lastUserCursor = res.data()?.search?.edges?.lastOrNull()?.cursor

                if (users != null) {
                    Observable.just(
                        PagingResponse(
                            items = users,
                            totalItemsCount = res.data()?.search?.userCount ?: 0,
                            lastCursor = lastUserCursor
                        )
                    )
                } else {
                    Observable.empty()
                }
            }
    }
}