package com.skifapp.skif_map

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder


@Serializable
data class Access(
    val id: String,
    @SerialName("id_user")
    val idUser: String
)

@Serializable
data class Watcher(
    val name: String,
    val topic: String,
    val phone : String,
    @SerialName("date_to")
    val dateTo : String,
    @SerialName("date_from")
    val dateFrom : String,
    val jdata : Map<String,String>
)
@Serializable
data class MqttData(
    @SerialName("speedkph")
    val speed: Int,
    @SerialName("pwr_ext")
    val power: Int,
    @SerialName("latlon")
    val latLon: LatLng,
    val datepoint : String,
    @SerialName("imei_md5")
    val imei : String
){
    override fun equals(other: Any?): Boolean {
        return if (other is MqttData){
            other.imei == imei
        }else{
            super.equals(other)
        }
    }
}

@Serializable(LatLngSerializer::class)
data class LatLng(
    val latitude: Double,
    val longitude: Double
)

fun LatLngfromString(string: String): LatLng =
    if (string.lastOrNull() == ')') {
        val s = string.substringAfter('(').substringBefore(')')
        val coord = s.split(' ')
        LatLng(
            coord[0].toDouble(), coord[1].toDouble()
        )
    } else throw SerializationException("Cant convert latlng string to LatLng")

object LatLngSerializer : KSerializer<LatLng> {

    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("LatLng", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): LatLng {
        val string = decoder.decodeString()
        return LatLngfromString(string)
    }

    override fun serialize(encoder: Encoder, value: LatLng) {
        encoder.encodeString("SRID=4326;POINT (${value.latitude} ${value.longitude})")
    }

}