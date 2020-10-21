package rpl1pnp.fikri.footballapps.database

data class FavoriteTeam(
    val id: Long?,
    val idTeam: String?,
    val team: String?,
    val teamBadge: String?
) {
    companion object {
        const val TABLE_FAVORITE_TEAM: String = "TABLE_FAVORITE_TEAM"
        const val ID: String = "ID_"
        const val ID_TEAM: String = "ID_TEAM"
        const val TEAM: String = "TEAM"
        const val TEAM_BADGE: String = "TEAM_BADGE"
    }
}