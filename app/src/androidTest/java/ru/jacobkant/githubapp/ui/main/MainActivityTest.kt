package ru.jacobkant.githubapp.ui.main

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.Response
import com.github.UsersQuery
import io.mockk.every
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.jacobkant.githubapp.App
import ru.jacobkant.githubapp.R
import ru.jacobkant.githubapp.data.githubApi.graphql.GithubApi
import ru.jacobkant.githubapp.data.preferences.DefaultPreferences
import ru.jacobkant.githubapp.data.vkApi.VkApi
import ru.jacobkant.githubapp.di.AppModule
import ru.jacobkant.githubapp.domain.MyProfileInfo
import ru.jacobkant.githubapp.ui.MainActivity
import ru.testDi.DaggerTestAppComponent
import ru.testDi.TestAppComponent
import ru.testUtils.withRecycler
import javax.inject.Inject


@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val activityRule: ActivityTestRule<MainActivity> =
        ActivityTestRule(MainActivity::class.java, false, false)

    @Inject
    lateinit var prefs: DefaultPreferences

    @Inject
    lateinit var vkApi: VkApi

    @Inject
    lateinit var githubApi: GithubApi

    @Before
    fun before() {
//        RxJavaPlugins.setInitComputationSchedulerHandler(
//            Rx2Idler.create("RxJava 2.x Computation Scheduler"));
//        RxJavaPlugins.setInitIoSchedulerHandler(
//            Rx2Idler.create("RxJava 2.x IO Scheduler"));
        val app =
            InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as App
        val testAppComponent: TestAppComponent = DaggerTestAppComponent.builder()
            .appModule(AppModule(app))
            .build()
        testAppComponent.inject(this)
        app.setAppComponent(testAppComponent)
    }

    @Test
    fun login_screen_showed_if_not_logged_in() {
        every { prefs.vkToken } returns ""
        activityRule.launchActivity(null)
        onView(withId(R.id.main_fragment_login_button))
            .check(matches(isDisplayed()))
            .check(matches(isClickable()))
            .check(matches(isEnabled()))
    }

    @Test
    fun main_screen_showed_if_logged_in() {
        every { vkApi.requestMyUser() } returns Single.error(Exception())
        every { prefs.vkToken } returns "test_token"
        activityRule.launchActivity(null)
        onView(withId(R.id.recycler_placeholder_message))
            .check(matches(isDisplayed()))
            .check(matches(withText(R.string.users_empty_placeholder)))
    }

    @Test
    fun current_user_showed_left_menu() {
        every { prefs.vkToken } returns "test_token"
        every { vkApi.requestMyUser() } returns Single.just(
            MyProfileInfo(
                "https://cdn3.iconfinder.com/data/icons/generic-avatars/128/avatar_portrait_woman_female_1-128.png",
                "firstName",
                "lastName",
                "user1"
            )
        )

        activityRule.launchActivity(null)

        onView(withContentDescription(R.string.hamburger_description)).perform(click())
        onView(withId(R.id.user_tv_username))
            .check(matches(isDisplayed()))
            .check(matches(withText("firstName lastName")))
        onView(withId(R.id.user_tv_login))
            .check(matches(isDisplayed()))
            .check(matches(withText("user1")))
    }


    @Test
    fun type_search_show_users_result() {
        every { prefs.vkToken } returns "test_token"
        every { vkApi.requestMyUser() } returns Single.error(Exception())
        every { githubApi.queryUsers(any(), any(), any()) } returns Observable.just(
            Response.builder<UsersQuery.Data>(
                UsersQuery(
                    "Robert C. Martin",
                    30,
                    Input.fromNullable(null)
                )
            )
                .data(
                    UsersQuery.Data(
                        UsersQuery.Search(
                            userCount = 1,
                            edges = listOf(
                                UsersQuery.Edge(
                                    cursor = "cursor",
                                    node = UsersQuery.Node(
                                        asUser = UsersQuery.AsUser(
                                            id = "id",
                                            avatarUrl = "https://cdn3.iconfinder.com/data/icons/generic-avatars/128/avatar_portrait_woman_female_1-128.png",
                                            name = "Robert Martin",
                                            email = "robert@gmail.com"
                                        )
                                    )
                                )
                            )
                        )
                    )
                )
                .build()
        )

        activityRule.launchActivity(null)

        onView(withId(R.id.menu_search)).perform(click())
        onView(withId(androidx.appcompat.R.id.search_src_text)).perform(typeText("Robert C. Martin"))

        // todo разобраться с ошибкой idling resources для rxjava
        // https://github.com/square/RxIdler/pull/28
        Thread.sleep(1000)

        onView(
            withRecycler(R.id.frag_users_recycler)
                .atPositionOnView(0, R.id.item_user_name)
        )
            .check(matches(withText("Robert Martin")))
    }

}