package com.patric.skeleton

import com.patric.core.base.BaseViewModel
import com.patric.core.utils.ErrorApp
import com.patric.core.utils.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val navigator: Navigator,
    private val errorApp: ErrorApp
) : BaseViewModel<AppState, AppEvent>(AppState()) {
    override fun onEvent(event: AppEvent) {
        when (event) {
            is AppEvent.Logout -> {}
        }
    }


}