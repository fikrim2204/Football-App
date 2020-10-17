package rpl1pnp.fikri.footballapps.model

import com.google.gson.annotations.SerializedName

data class Events(
    @SerializedName("idEvent")
    var eventId: String? = null,

    @SerializedName("idLeague")
    var idLeague: String? = null,

    @SerializedName("idHomeTeam")
    var homeTeamId: String? = null,

    @SerializedName("idAwayTeam")
    var awayTeamId: String? = null,

    @SerializedName("strEvent")
    var eventName: String? = null,

    @SerializedName("strHomeTeam")
    var homeTeam: String? = null,

    @SerializedName("strAwayTeam")
    var awayTeam: String? = null,

    @SerializedName("intHomeScore")
    var homeScore: String? = null,

    @SerializedName("intAwayScore")
    var awayScore: String? = null,

    @SerializedName("strHomeFormation")
    var homeFormation: String? = null,

    @SerializedName("strAwayFormation")
    var awayFormation: String? = null,

    @SerializedName("dateEvent")
    var dateEvent: String? = null,

    @SerializedName("strHomeGoalDetails")
    var homeGoalDetail: String? = null,

    @SerializedName("strAwayGoalDetails")
    var awayGoalDetail: String? = null,

    @SerializedName("strHomeRedCards")
    var homeRedCard: String? = null,

    @SerializedName("strHomeYellowCards")
    var homeYellowCard: String? = null,

    @SerializedName("strAwayRedCards")
    var awayRedCard: String? = null,

    @SerializedName("strAwayYellowCards")
    var awayYellowCard: String? = null,

    @SerializedName("strTime")
    var time: String? = null,

    @SerializedName("strSport")
    var sport: String? = null
)