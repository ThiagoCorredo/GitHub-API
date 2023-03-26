package com.tcorredo.githubapi

import androidx.test.espresso.IdlingRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tcorredo.githubapi.di.appModule
import com.tcorredo.githubapi.di.domainModule
import com.tcorredo.githubapi.di.testModule
import com.tcorredo.githubapi.di.viewModelModule
import com.tcorredo.githubapi.util.ViewVisibilityIdlingResource
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.context.GlobalContext.stopKoin
import org.koin.test.KoinTest

@RunWith(AndroidJUnit4::class)
abstract class BaseEspressoTest : KoinTest {
    protected val mockWebServer = MockWebServer()
    protected lateinit var progressBarGoneIdlingResource: ViewVisibilityIdlingResource

    @Before
    open fun setUp() {
        stopKoin()
        startKoin { modules(testModule, viewModelModule, domainModule, appModule) }

        mockWebServer.start(8080)
        mockWebServer.serverSocketFactory
    }

    @After
    open fun tearDown() {
        stopKoin()
        mockWebServer.shutdown()
        IdlingRegistry.getInstance().unregister(progressBarGoneIdlingResource)
    }
}