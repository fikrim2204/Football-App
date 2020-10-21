package rpl1pnp.fikri.footballapps.view

import rpl1pnp.fikri.footballapps.database.FavoriteMatch

interface FavoriteView {
    fun showFavorite(data: List<FavoriteMatch>)
}