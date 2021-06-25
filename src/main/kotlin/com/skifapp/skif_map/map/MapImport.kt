package com.skifapp.skif_map.map

import react.RClass
import react.RProps

val reactLeafletModule =  io.kvision.require("react-leaflet")

val MapContainer: RClass<MapContainerProps> = reactLeafletModule.MapContainer

val TileLayer : RClass<TileLayerProps> =reactLeafletModule.TileLayer

val Marker : RClass<MarkerProps>  = reactLeafletModule.Marker

val Popup : RClass<RProps> = reactLeafletModule.Popup

external interface MapContainerProps : RProps {
    var zoom : Int
    var scrollWheelZoom : Boolean
    var center : Array<Double>
}

external interface TileLayerProps : RProps{
    var url : String
    var attribution : String
}

external interface MarkerProps : RProps{
    var position : Array<Double>
    var icon : dynamic
}


