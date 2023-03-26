package com.tcorredo.githubapi.project

import android.content.Intent
import android.view.View
import androidx.core.widget.ContentLoadingProgressBar
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tcorredo.githubapi.BaseEspressoTest
import com.tcorredo.githubapi.R
import com.tcorredo.githubapi.ui.MainActivity
import com.tcorredo.githubapi.util.ViewVisibilityIdlingResource
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProjectFragmentTest : BaseEspressoTest() {


    @Test
    fun onAppLaunch_shouldShowLoadingList() {
        onView(withId(R.id.loadingProgress)).check(matches(isDisplayed()))
    }

    @Test
    fun onAppLaunch_whenLoadStopAndResponseSuccess_shouldShowProjectList() {
        mockWebServer.dispatcher = RepositorySuccessDispatcher()

        val intent = Intent(ApplicationProvider.getApplicationContext(), MainActivity::class.java)
        val scenario = ActivityScenario.launch<MainActivity>(intent)

        var loadingProgress: ContentLoadingProgressBar? = null
        scenario.onActivity {
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

    @Test
    fun onAppLaunch_whenLoadStopAndResponseFailure_shouldErrorLayout() {
        mockWebServer.dispatcher = ErrorDispatcher()

        val intent = Intent(ApplicationProvider.getApplicationContext(), MainActivity::class.java)
        val scenario = ActivityScenario.launch<MainActivity>(intent)

        var loadingProgress: ContentLoadingProgressBar? = null
        scenario.onActivity {
            loadingProgress = it.findViewById(R.id.loadingProgress)
        }

        progressBarGoneIdlingResource =
            ViewVisibilityIdlingResource(
                loadingProgress,
                View.GONE
            )

        ProjectRobot()
            .waitForCondition(progressBarGoneIdlingResource)
            .assertErrorDisplayed()
    }
}