package rpl1pnp.fikri.footballmatchschedule.network

object Constant {
    const val BASE_URL: String = rpl1pnp.fikri.footballmatchschedule.BuildConfig.BASE_URL
    private const val API = "/api"
    private const val VERSION = "/v1"
    private const val JSON = "/json"
    private const val API_KEY: String = rpl1pnp.fikri.footballmatchschedule.BuildConfig.TSDB_API_KEY
    const val SEARCH = "/searchevents.php?e="
    const val LOOKUPEVENT = "/lookupevent.php?id="
    const val PREVIOUSMATCH = "/eventspastleague.php?id="
    const val NEXTMATCH = "/eventsnextleague.php?id="
    const val LOOKUPLEAGUE = "/lookupleague.php"
    const val LOOKUPTEAMS = "/lookupteam.php?id="
    const val PATH = API + VERSION + JSON + API_KEY
}