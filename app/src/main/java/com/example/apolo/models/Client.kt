package com.example.apolo.models

import com.squareup.moshi.Json

data class Client(

    val id: String,
    val name: String, //nome do EC
    val address: String, //"address": "Rua Goomes Carneiro",
    val tpv: Double, // valor em rais de transações por mes
    val segment: String, //tipo de seguimento do EC
    @Json(name = "last_visit") val lastVisit: String, // data da ultima visita
    @Json(name = "next_visit") val nextVisit: String, // data da prx visita "12/01/2021",
    val lat: Double,
    val lng: Double,
    val satisfaction: String // nivel de satisfação do cliente
)