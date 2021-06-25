package com.skifapp.skif_map.network

import com.skifapp.skif_map.Access
import com.skifapp.skif_map.Watcher
import com.skifapp.skif_map.utils.BASE_MQTT_GPS_PATH

class Mqtt(){
    var client : Paho.Client? = null

    fun mqttConnect(access: Access, watchers: List<Watcher?>,onMessage : (String) -> Unit) {
        console.log("Connecting to MQTT")
        val hostname = "skifgate.ru"
        val port = 443
        val clientId = access.id
        val username = access.idUser
        val password = access.id
        client = Paho.Client(hostname, port, "/ws", clientId)
        val opt: Paho.ConnectOptions = js("({})")
        opt.password = password
        opt.userName = username
        opt.onSuccess = {
            console.log("SUCCESS")
            watchers.forEach { watcher ->
                client?.subscribe(BASE_MQTT_GPS_PATH + watcher?.topic.toString() + "/addr/1")
            }
        }
        opt.onFailure = {
            console.log("FAILURE ${it.errorCode} ${it.errorMessage}")
        }
        opt.reconnect = true
        opt.useSSL = true
        client?.onMessageArrived = { msg ->
            onMessage(msg.payloadString)
        }
        client?.connect(opt)
    }

}