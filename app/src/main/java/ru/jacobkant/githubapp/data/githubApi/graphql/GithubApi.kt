package ru.jacobkant.githubapp.data.githubApi.graphql

import com.apollographql.apollo.api.Response
import com.github.UsersQuery
import io.reactivex.Observable


interface GithubApi {
    fun queryUsers(query: String, pageSize: Int, afterCursor: String?): Observable<Response<UsersQuery.Data>>
}

