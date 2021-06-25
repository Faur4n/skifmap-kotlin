package com.skifapp.skif_map.network

@JsModule("paho-mqtt")
@JsNonModule
external object Paho {
        class Client(host: String, port: Int,path: String, clientId: String) {
            val host: String
            val port: Int
            val clientId: String
            val path : String
            fun connect(connectOptions: ConnectOptions)
            fun send(message: Message)
            fun subscribe(destination: String)
            fun disconnect()
            var onMessageArrived: (msg: Message) -> Unit
            var onConnectionLost: (response: Response) -> Unit
        }

        class Message(payloadString: String) {
            val payloadString: String
            var destinationName: String
        }

        interface Response {
            val errorCode: Int
            val errorMessage: String
        }

        interface ConnectOptions {
            var userName: String
            var password: String
            var reconnect: Boolean
            var useSSL: Boolean
            var onSuccess: (response: dynamic) -> Unit
            var onFailure: (response: Response) -> Unit
        }
}