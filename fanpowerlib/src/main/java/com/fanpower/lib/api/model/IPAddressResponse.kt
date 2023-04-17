package com.fanpower.lib.api.model

data class IPAddressResponse(
    val fanCity: String,
    val fanCountry: String,
    val fanGeoLocation: String,
    val fanState: String,
    val fanTimeZone: String,
    val fanZipCode: String,
    val ipAddress: String,
    val code : String,
    val error : String,
    val url : String


) : java.io.Serializable