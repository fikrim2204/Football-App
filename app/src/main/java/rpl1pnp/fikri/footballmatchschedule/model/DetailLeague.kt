package rpl1pnp.fikri.footballmatchschedule.model

import com.google.gson.annotations.SerializedName

data class DetailLeague(
    @SerializedName("idLeague")
    var leagueId: Int? = null,

    @SerializedName("strLeague")
    var leagueName: String? = null,

    @SerializedName("strBadge")
    var leagueBadge: Int? = null,

    @SerializedName("strDescriptionEN")
    var leagueDescription: String? = null
)