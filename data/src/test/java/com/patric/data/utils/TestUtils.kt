package com.patric.data.utils

import com.patric.core.dispacher.unconfined
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before

open class TestUtils {

    @Before
    fun setUp(){
        MockKAnnotations.init(this)
        Dispatchers.setMain(unconfined)
    }

    @After
    fun clean() {
        clearAllMocks()
    }
}