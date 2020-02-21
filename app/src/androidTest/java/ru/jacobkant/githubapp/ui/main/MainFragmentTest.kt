package ru.jacobkant.githubapp.ui.main

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.jacobkant.githubapp.MainActivity
import ru.jacobkant.githubapp.R

@RunWith(AndroidJUnit4::class)
class MainFragmentTest {

    @get:Rule
    val activityRule: ActivityTestRule<MainActivity> =
        ActivityTestRule(MainActivity::class.java, true, true)

    @Before
    fun before() {
        activityRule.activity.supportFragmentManager.beginTransaction()
            .replace(R.id.container, MainFragment())
            .commitAllowingStateLoss()
    }

    @Test
    fun test_initial_view_state() {
        onView(withId(R.id.main_fragment_login_button))
            .check(matches(isDisplayed()))
            .check(matches(isClickable()))
            .check(matches(isEnabled()))
    }

}