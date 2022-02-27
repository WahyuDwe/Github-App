package com.wahyudwi.githubapp.utils

import androidx.test.espresso.idling.CountingIdlingResource

object EspressoIdlingResource {
    private const val RESOURCE = "GLOBAL"

    val idlingResource: CountingIdlingResource = CountingIdlingResource(RESOURCE)
    fun increment() = idlingResource.increment()
    fun decrement() = idlingResource.decrement()
}