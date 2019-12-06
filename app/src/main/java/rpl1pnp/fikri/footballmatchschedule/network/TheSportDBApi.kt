package rpl1pnp.fikri.footballmatchschedule.network

import rpl1pnp.fikri.footballmatchschedule.BuildConfig

object TheSportDBApi {
    public fun getTeams(league: String?): String {
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" +
                "/search_all_teams.php?l=" + league
    }

    public fun getLeagueDetail(idLeague: Int?): String {
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" +
                "/lookupleague.php?id=" + idLeague
    }

    public fun getNextMatch(idLeague: String?): String {
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" +
                "/eventsnextleague.php?id=" + idLeague
    }

    public fun getPreviousMatch(idLeague: String?): String {
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" +
                "/eventspastleague.php?id=" + idLeague
    }

    public fun getDetailMatch(idEvent: String?): String {
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" +
                "/lookupevent.php?id=" + idEvent
    }

    public fun getSearch(query: String?): String {
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" +
                "/searchevents.php?e=" + query
    }
}