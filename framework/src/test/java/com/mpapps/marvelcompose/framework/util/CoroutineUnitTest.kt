package com.mpapps.marvelcompose.framework.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
open class CoroutineUnitTest {
    val scope = TestScope()

    lateinit var dispatcher: TestDispatcher

    @Before
    fun setUpCoroutines() {

        dispatcher = StandardTestDispatcher(scope.testScheduler)
        Dispatchers.setMain(dispatcher)
        MockitoAnnotations.openMocks(this)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    open fun runBlockingTest(block: suspend TestScope.() -> Unit) {
        scope.runTest(testBody = block)
    }

    inline fun <reified T> any(): T = Mockito.any(T::class.java)

}