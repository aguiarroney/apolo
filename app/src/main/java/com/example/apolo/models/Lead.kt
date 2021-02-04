package com.example.apolo.models

import com.squareup.moshi.Json

data class Lead(

    val id: String,
    val name: String, //nome do EC
    val address: String, //"address": "Rua Goomes Carneiro",
    val tpv: Double, // valor em rais de transações por mes
    val status: String, //status da negociação quente/fria
    @Json(name = "qnt_visits") val visitQnt: String, // quantidade de visitas
    @Json(name = "next_visit") val nextVisit: String, // data da prx visita "12/01/2021",
    val lat: Double,
    val lng: Double
)