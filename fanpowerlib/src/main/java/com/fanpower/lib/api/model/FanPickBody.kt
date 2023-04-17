package com.fanpower.lib.api.model

class FanPickBody {

    lateinit var pick_id : String
    lateinit var prop_id : String
    lateinit var source_url : String
    lateinit var publisher_id : String
    lateinit var fan_id : String
    lateinit var ip_address : String
    lateinit var fanCity : String
    lateinit var proposition : Proposition
    lateinit var pick : PickCreateParam
    lateinit var publisherInfo: PublisherInfo
    lateinit var fanState : String
    lateinit var fanCountry : String
    lateinit var fanZipCode : String
    lateinit var fanGeoLocation : String
    lateinit var fanTimeZone : String
    lateinit var version : String

}