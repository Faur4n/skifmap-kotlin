package com.skifapp.skif_map.utils

import kotlinx.browser.window
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.serialization.json.Json


val AppScope = CoroutineScope(window.asCoroutineDispatcher())

val token = "\$5\$rounds=535000\$yoeDJckLYpQT6UBt\$CQimLxshnEm.OTrWWpBLVFbzTnSgu009JxLWHWAQ0I2"

const val BASE_MQTT_GPS_PATH = "debug/"

val json = Json {
    isLenient = true
    ignoreUnknownKeys = true
}

inline fun <T> jsApply(init: dynamic, cb: T.() -> Unit): T {
    cb(init.unsafeCast<T>())
    return init.unsafeCast<T>()
}

inline fun <T> jsLet(init: dynamic, cb: (T) -> Unit): T {
    cb(init.unsafeCast<T>())
    return init.unsafeCast<T>()
}
