package rpl1pnp.fikri.footballapps.network

object Constant {
    const val BASE_URL: String = rpl1pnp.fikri.footballapps.BuildConfig.BASE_URL
    private const val API = "/api"
    private const val VERSION = "/v1"
    private const val JSON = "/json"
    private const val API_KEY: String = rpl1pnp.fikri.footballapps.BuildConfig.TSDB_API_KEY
    const val SEARCHMATCH = "/searchevents.php?e="
    const val SEARCHTEAM = "/searchteams.php?t="
    const val LOOKUPEVENT = "/lookupevent.php?id="
    const val LASTMATCH = "/eventspastleague.php?id="
    const val NEXTMATCH = "/eventsnextleague.php?id="
    const val LOOKUPLEAGUE = "/lookupleague.php?id="
    const val LOOKUPTEAMS = "/lookupteam.php?id="
    const val LOOKUPALLTEAMS = "/lookup_all_teams.php?id="
    const val LOOKUPTABLE = "/lookuptable.php?l="
    const val PATH = API + VERSION + JSON + API_KEY
}