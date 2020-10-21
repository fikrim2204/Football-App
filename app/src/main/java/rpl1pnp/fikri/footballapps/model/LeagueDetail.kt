package rpl1pnp.fikri.footballapps.model

import com.google.gson.annotations.SerializedName

data class LeagueDetail(
    @SerializedName("idLeague")
    var leagueId: String? = null,

    @SerializedName("strLeague")
    var leagueName: String? = null,

    @SerializedName("strDescriptionEN")
    var leagueDescription: String? = null,

    @SerializedName("strBadge")
    var leagueBadge: String? = null,

    @SerializedName("strLogo")
    var leagueLogo: String? = null,

    @SerializedName("strBanner")
    var leagueBanner: String? = null
)