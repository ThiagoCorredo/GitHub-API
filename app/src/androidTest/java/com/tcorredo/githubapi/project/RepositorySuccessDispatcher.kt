package com.tcorredo.githubapi.project

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import com.tcorredo.githubapi.util.FileReader.asset
import com.tcorredo.githubapi.util.MockFiles.REPOSITORY_RESPONSE_SUCCESS
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

class RepositorySuccessDispatcher(
    private val context: Context = InstrumentationRegistry.getInstrumentation().context
) : Dispatcher() {

    override fun dispatch(request: RecordedRequest): MockResponse {
        val responseBody = asset(context, REPOSITORY_RESPONSE_SUCCESS)
        return MockResponse().setResponseCode(200).setBody(responseBody)
    }
}