package rpl1pnp.fikri.footballapps.view

import rpl1pnp.fikri.footballapps.database.FavoriteMatch

interface FavoriteMatchView {
    fun showFavoriteMatch(data: List<FavoriteMatch>)
}