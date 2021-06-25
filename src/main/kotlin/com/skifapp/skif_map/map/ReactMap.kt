package com.skifapp.skif_map.map

import com.skifapp.skif_map.Access
import com.skifapp.skif_map.LatLng
import com.skifapp.skif_map.MqttData
import com.skifapp.skif_map.Watcher
import com.skifapp.skif_map.network.Api
import com.skifapp.skif_map.network.Mqtt
import com.skifapp.skif_map.utils.json
import io.kvision.core.Col
import io.kvision.core.Color
import io.kvision.html.li
import io.kvision.html.p
import io.kvision.html.ul
import io.kvision.react.kv
import kotlinx.browser.window
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.decodeFromJsonElement
import react.*
import react.dom.div


external interface MapProps : RProps {
    var token: String?
    var onNewMessage: (Set<MqttData?>) -> Unit
}

external interface MapState : RState {
    var data: MutableSet<MqttData?>
}

@JsExport
class ReactMap(props: MapProps) : RComponent<MapProps, MapState>(props) {

    private val onNewMessage: (String) -> Unit = {
        console.log(it)
        val data = json.decodeFromString<MqttData>(it)
        setState {
            this.data.add(data)
        }
        props.onNewMessage(state.data)
    }

    override fun MapState.init(props: MapProps) {
        CoroutineScope(window.asCoroutineDispatcher()).launch {
            val mqtt = Mqtt()
            val accessDef = async {
                Api.route<Access>("/api/acces")
            }
            val watcherDef = async {
                Api.route<List<Watcher>>("/api/watchers")
            }
            mqtt.mqttConnect(accessDef.await(), watcherDef.await(), onNewMessage)
        }
    }

    override fun RBuilder.render() {
        MapContainer {
            attrs.center = arrayOf(55.751244, 37.618423)
            attrs.scrollWheelZoom = true
            attrs.zoom = 8
            TileLayer {
                attrs.attribution = "&copy; <a href=\"http://osm.org/copyright\">OpenStreetMap</a> contributors"
                attrs.url = "https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
            }
            try {
                state.data.filterNotNull().forEach { data ->
                    Marker {
                        attrs.position = arrayOf(data.latLon.latitude, data.latLon.longitude)
                        Popup {
                            kv {
                                ul {
                                    li {
                                        +"Дата: ${data.datepoint}"
                                    }
                                    li {
                                        +"Скорость: ${data.speed}"
                                    }
                                    li {
                                        +"Заряд: ${data.power}"
                                    }
                                }
                            }
                        }
                    }
                }
            }catch (th: Throwable){

            }
        }
    }
}

fun RBuilder.map(handler: MapProps.() -> Unit): ReactElement {
    return child(ReactMap::class) {
        this.attrs(handler)
    }
}
