package com.mpapps.marvelcompose

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.mpapps.marvelcompose.framework.datasources.local.cache.service.MemoryCacheService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var memoryCacheService: MemoryCacheService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        memoryCacheService.clearAll()
        return super.onCreateView(name, context, attrs)
    }
}