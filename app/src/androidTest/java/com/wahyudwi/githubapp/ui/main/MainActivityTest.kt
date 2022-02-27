package com.wahyudwi.githubapp.ui.main

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.wahyudwi.githubapp.R
import com.wahyudwi.githubapp.utils.DataDummy
import com.wahyudwi.githubapp.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainActivityTest {
    private val userDummy = DataDummy.dataDummyUser()

    @get:Rule
    var activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        ActivityScenario.launch(MainActivity::class.java)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }

    @Test
    fun testSearchUser() {
        onView(withId(R.id.search_menu)).check(matches(isDisplayed()))
        onView(withId(R.id.search_menu)).perform(click())
        onView(withId(androidx.appcompat.R.id.search_src_text))
            .perform(typeText("a"))
        onView(withId(R.id.rv_user)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_user)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                userDummy.size
            )
        )
    }

    @Test
    fun testDetailUser() {
        onView(withId(R.id.search_menu)).perform(click())
        onView(withId(androidx.appcompat.R.id.search_src_text)).perform(typeText("d"))
        onView(withId(R.id.rv_user)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.iv_detail_user)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_detail_name)).check(matches(isDisplayed()))
        onView(withId(R.id.iv_detail_user)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_detail_company)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_detail_location)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_detail_repository)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_detail_followers)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_detail_following)).check(matches(isDisplayed()))
    }

    @Test
    fun testFollowers() {
        onView(withId(R.id.search_menu)).perform(click())
        onView(withId(androidx.appcompat.R.id.search_src_text)).perform(typeText("d"))
        onView(withId(R.id.rv_user)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.rv_user_followers)).check(matches(isDisplayed()))
        onView(withId(R.id.coordinator_layout)).perform(swipeUp())
    }

    @Test
    fun testFollowing() {
        onView(withId(R.id.search_menu)).perform(click())
        onView(withId(androidx.appcompat.R.id.search_src_text)).perform(typeText("d"))
        onView(withId(R.id.rv_user)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(withText(R.string.following)).perform(click())
        onView(withId(R.id.rv_user_following)).check(matches(isDisplayed()))
        onView(withId(R.id.coordinator_layout)).perform(swipeUp())
    }

    @Test
    fun testFavUser() {
        onView(withId(R.id.search_menu)).perform(click())
        onView(withId(androidx.appcompat.R.id.search_src_text)).perform(typeText("d"))
        onView(withId(R.id.rv_user)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.fab_favorite)).perform(click())
        onView(isRoot()).perform(pressBack())
        onView(withId(androidx.appcompat.R.id.search_close_btn)).perform(click())
        onView(withId(androidx.appcompat.R.id.search_close_btn)).perform(click())
        onView(withId(R.id.favorite_menu)).perform(click())
        onView(withId(R.id.rv_user_favorite)).check(matches(isDisplayed()))
    }

    @Test
    fun testDetailFavUser() {
        onView(withId(R.id.favorite_menu)).perform(click())
        onView(withId(R.id.rv_user_favorite)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_user_favorite)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.iv_detail_user)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_detail_name)).check(matches(isDisplayed()))
        onView(withId(R.id.iv_detail_user)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_detail_company)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_detail_location)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_detail_repository)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_detail_followers)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_detail_following)).check(matches(isDisplayed()))
    }

    @Test
    fun testDarkMode() {
        onView(withId(R.id.setting_menu)).perform(click())
        onView(withId(R.id.switch_theme)).perform(click())
        onView(withId(R.id.switch_theme)).check(matches(isChecked()))
    }
}