package com.tcorredo.githubapi.util

import android.net.Uri
import okhttp3.Headers

class PagingManager {

    companion object {
        private val NEXT_PAGE_LINK = ".*<(.+)>; rel=\"next\".*".toPattern()
        private val LAST_PAGE_LINK = ".*<(.+)>; rel=\"last\".*".toPattern()
    }

    var nextPage: Int = 1
    var lastPage: Int = 0

    fun savePageHeader(headers: Headers) {
        val link = headers.get("Link") ?: ""

        val nextMatcher = NEXT_PAGE_LINK.matcher(link)
        if (nextMatcher.matches()) {
            val nextUri = Uri.parse(nextMatcher.group(nextMatcher.groupCount()))
            val next = nextUri.getQueryParameter("page")
            nextPage = next?.toInt() ?: lastPage
        }

        val lastMatcher = LAST_PAGE_LINK.matcher(link)
        if (lastMatcher.matches()) {
            val lastUri = Uri.parse(lastMatcher.group(lastMatcher.groupCount()))
            val last = lastUri.getQueryParameter("page")
            lastPage = last?.toInt() ?: 0
        }
    }

    fun hasMore(): Boolean {
        return nextPage <= lastPage
    }
}