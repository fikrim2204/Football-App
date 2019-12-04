package rpl1pnp.fikri.footballmatchschedule

object TheSportDBApi {
    public fun getTeams(league: String?): String {
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" +
                "/search_all_teams.php?l=" + league
    }

    public fun getLeagueDetail(idLeague: Int?): String {
        return BuildConfig.BASE_URL + "api/v1/json" +
                "/lookupleague.php?id=" + idLeague
    }
}