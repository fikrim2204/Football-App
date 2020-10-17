package rpl1pnp.fikri.footballapps.view

import rpl1pnp.fikri.footballapps.database.Favorite

interface FavoriteView {
    fun showFavorite(data: List<Favorite>)
}