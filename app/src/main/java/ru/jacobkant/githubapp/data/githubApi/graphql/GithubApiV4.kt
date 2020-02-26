package ru.jacobkant.githubapp.data.githubApi.graphql

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.rx2.Rx2Apollo
import com.facebook.stetho.okhttp3.BuildConfig
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.github.UsersQuery
import io.reactivex.Observable
import okhttp3.OkHttpClient
import javax.inject.Inject

class GithubApiV4 @Inject constructor() : GithubApi {
    private val apolloClient: ApolloClient = ApolloClient.builder().apply {
        this.serverUrl("https://api.github.com/graphql")
        val okHttp = OkHttpClient.Builder()
            .authenticator(AuthentificatorGithub())
        if (BuildConfig.DEBUG) okHttp.addInterceptor(StethoInterceptor())
        this.okHttpClient(okHttp.build())
    }.build()


    override fun queryUsers(
        query: String,
        pageSize: Int,
        afterCursor: String?
    ): Observable<Response<UsersQuery.Data>> {
        return Rx2Apollo.from(
            apolloClient.query(
                UsersQuery(
                    query = query,
                    firstCount = pageSize,
                    afterCursor = Input.fromNullable(afterCursor)
                )
            )
        )
    }
}