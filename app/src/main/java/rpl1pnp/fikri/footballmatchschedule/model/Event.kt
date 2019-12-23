package rpl1pnp.fikri.footballmatchschedule.model

import com.google.gson.annotations.SerializedName

data class Event(
    @SerializedName("idEvent")
    var eventId: String? = null,

    @SerializedName("strEvent")
    var strEvent: String? = null,

    @SerializedName("strHomeTeam")
    var strHomeTeam: String? = null,

    @SerializedName("strAwayTeam")
    var strAwayTeam: String? = null,

    @SerializedName("dateEvent")
    var dateEvent: String? = null,

    @SerializedName("intHomeScore")
    var homeScore: String? = null,

    @SerializedName("intAwayScore")
    var awayScore: String? = null
)