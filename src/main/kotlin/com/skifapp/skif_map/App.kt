package com.skifapp.skif_map

import com.skifapp.skif_map.map.ReactMap
import com.skifapp.skif_map.map.map
import com.skifapp.skif_map.utils.token
import io.kvision.Application
import io.kvision.form.text.textInput
import io.kvision.html.*
import io.kvision.module
import io.kvision.panel.root
import io.kvision.react.react
import io.kvision.startApplication
import io.kvision.state.MutableState
import io.kvision.state.ObservableValue
import io.kvision.utils.px
import kotlinx.browser.window
import kotlinx.coroutines.*


class App : Application(), CoroutineScope by CoroutineScope(window.asCoroutineDispatcher()) {

    private val mqttState: MutableState<Set<MqttData?>> = ObservableValue(emptySet())
    private val onMessage: (Set<MqttData?>) -> Unit = {
        console.log(it)
        mqttState.setState(it)
    }

    init {

    }

    override fun start(state: Map<String, Any>) {
        console.log(state)
        root("kvapp") {
            header {
                padding = 24.px
                div {
                    image("https://skif.pro/images/skifpro.svg")
                }
            }
            main {
                val react = react() {
                    map {
                        token = token
                        onNewMessage = onMessage
                    }
                }
                react.height = 600.px
                div(mqttState) { state ->
                    state.forEach {
                        p {
                            +it.toString()
                        }
                    }

                }
            }
        }
    }

}


fun main() {
    startApplication(::App, module.hot)
}
