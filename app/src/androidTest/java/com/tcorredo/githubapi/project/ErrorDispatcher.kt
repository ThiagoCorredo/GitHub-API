package com.tcorredo.githubapi.project

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import com.tcorredo.githubapi.util.FileReader
import com.tcorredo.githubapi.util.MockFiles.REPOSITORY_RESPONSE_ERROR
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

class ErrorDispatcher(
    private val context: Context = InstrumentationRegistry.getInstrumentation().context
) : Dispatcher() {
    override fun dispatch(request: RecordedRequest): MockResponse {
        val responseBody = FileReader.asset(context, REPOSITORY_RESPONSE_ERROR)
        return MockResponse().setResponseCode(403).setBody(responseBody)
    }
}