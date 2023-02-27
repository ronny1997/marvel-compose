package com.patric.ui.utils

import java.util.*

fun String?.capitalize() =
    orEmpty().replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }