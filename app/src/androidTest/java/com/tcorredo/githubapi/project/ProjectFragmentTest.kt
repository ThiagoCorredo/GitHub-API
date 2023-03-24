package com.tcorredo.githubapi.project

import android.view.View
import androidx.core.widget.ContentLoadingProgressBar
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tcorredo.githubapi.R
import com.tcorredo.githubapi.ui.MainActivity
import com.tcorredo.githubapi.util.SuccessDispatcher
import com.tcorredo.githubapi.util.ViewVisibilityIdlingResource
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProjectFragmentTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    private val mockWebServer = MockWebServer()
    private var progressBarGoneIdlingResource: ViewVisibilityIdlingResource? = null

    @Before
    fun setUp() {
        mockWebServer.start(8080)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
        IdlingRegistry.getInstance().unregister(progressBarGoneIdlingResource)
    }

    @Test
    fun onAppLaunch_shouldShowLoadingList() {
        onView(withId(R.id.loadingProgress)).check(matches(isDisplayed()))
    }

    @Test
    fun onAppLaunch_whenLoadStopAndResponseSuccess_shouldShowProjectList() {
        mockWebServer.dispatcher = SuccessDispatcher()
        var loadingProgress: ContentLoadingProgressBar? = null
        activityRule.scenario.onActivity {
            loadingProgress = it.findViewById(R.id.loadingProgress)
        }

        progressBarGoneIdlingResource =
            ViewVisibilityIdlingResource(
                loadingProgress,
                View.GONE
            )
        ProjectRobot()
            .waitForCondition(progressBarGoneIdlingResource)
            .assertDataDisplayed()
    }
}