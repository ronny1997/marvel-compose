package com.patric.core.utils

import java.math.RoundingMode
import java.text.DecimalFormat

fun Double.fiveDecimalUtils():Double{
    val df = DecimalFormat("#.#####")
    df.roundingMode = RoundingMode.CEILING
    return  return df.format(this).replace(",", ".").toDouble()
}