package com.tcorredo.githubapi.data.domain.dispatchers

import kotlinx.coroutines.CoroutineDispatcher

interface CoroutineDispatchers {
  val main: CoroutineDispatcher
  val io: CoroutineDispatcher
}
