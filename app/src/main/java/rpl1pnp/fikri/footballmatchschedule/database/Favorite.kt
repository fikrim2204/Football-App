package rpl1pnp.fikri.footballmatchschedule.database

data class Favorite(
    val id: Long?,
    val idEvent: String?,
    val idLeague: String?,
    val idHomeTeam: String?,
    val idAwayTeam: String?,
    val homeTeam: String?,
    val awayTeam: String?,
    val homeScore: String?,
    val awayScore: String?,
    val dateEvent: String?,
    val time: String?
) {
    companion object {
        const val TABLE_FAVORITE: String = "TABLE_FAVORITE"
        const val ID: String = "ID_"
        const val ID_EVENT: String = "ID_EVENT"
        const val ID_LEAGUE: String = "ID_LEAGUE"
        const val ID_HOME_TEAM: String = "ID_HOME_TEAM"
        const val ID_AWAY_TEAM: String = "ID_AWAY_TEAM"
        const val HOME_TEAM: String = "HOME_TEAM"
        const val AWAY_TEAM: String = "AWAY_TEAM"
        const val HOME_SCORE: String = "HOME_SCORE"
        const val AWAY_SCORE: String = "AWAY_SCORE"
        const val DATE_EVENT: String = "DATE_EVENT"
        const val TIME: String = "TIME"
    }
}