package com.skifapp.skif_map.network

import com.skifapp.skif_map.utils.token
import io.kvision.rest.HttpMethod
import io.kvision.rest.RestClient
import kotlinx.coroutines.await

object Api {

    val client = RestClient()

    suspend inline fun <reified T : Any> route(url: String): T {

        return client.request<T>(
            url = url,
            method = HttpMethod.POST,
            beforeSend = { JQueryXHR, _ ->
                JQueryXHR.setRequestHeader("token", token)
                true
            }
        ).await().data
    }
}