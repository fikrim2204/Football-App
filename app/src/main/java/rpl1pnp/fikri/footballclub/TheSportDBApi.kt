package rpl1pnp.fikri.footballclub

object TheSportDBApi {
    public fun getTeams(league: String?): String {
        return BuildConfig.BASE_URL + "api/vi/json/${BuildConfig.TSDB_API_KEY}" +
                "/search_all_teams.php?l=" + league
    }
}