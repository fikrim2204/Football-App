package rpl1pnp.fikri.footballmatchschedule.view

import rpl1pnp.fikri.footballmatchschedule.database.Favorite

interface FavoriteView {
    fun showFavorite(data: List<Favorite>)
}