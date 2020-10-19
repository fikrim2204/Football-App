package rpl1pnp.fikri.footballapps.view

import rpl1pnp.fikri.footballapps.model.Events
import rpl1pnp.fikri.footballapps.model.Teams

interface DetailMatchView {
    fun showLoading()
    fun hideLoading()
    fun showDetail(data: List<Events>)
    fun getLogoTeam(data: List<Teams>, isHomeTeam: Boolean)
    fun addFavorite()
    fun removeFavorite()
    fun favoriteState(state: Boolean)
    fun errorFavorite(message: CharSequence)
}