package rpl1pnp.fikri.footballapps.view

import rpl1pnp.fikri.footballapps.database.FavoriteTeam

interface FavoriteTeamView {
    fun showFavoriteTeam(data: List<FavoriteTeam>)
}