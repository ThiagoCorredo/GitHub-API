package com.tcorredo.githubapi.project

import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.tcorredo.githubapi.R

class ProjectRobot {

    fun assertDataDisplayed() = apply {
        Espresso.onView(recyclerViewMatcher)
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    fun assertErrorDisplayed() = apply {
        Espresso.onView(errorViewMatcher).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    fun waitForCondition(idlingResource: IdlingResource?) = apply {
        IdlingRegistry.getInstance().register(idlingResource)
    }

    companion object {
        private val recyclerViewMatcher = withId(R.id.recyclerView)
        private val errorViewMatcher = withId(R.id.infoLayout)
    }
}
