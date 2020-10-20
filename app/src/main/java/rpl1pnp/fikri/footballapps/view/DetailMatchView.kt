package rpl1pnp.fikri.footballapps.view

import rpl1pnp.fikri.footballapps.model.Event
import rpl1pnp.fikri.footballapps.model.Team

interface DetailMatchView {
    fun showLoading()
    fun hideLoading()
    fun showDetail(data: List<Event>)
    fun getLogoTeam(data: List<Team>, isHomeTeam: Boolean)
    fun addFavorite()
    fun removeFavorite()
    fun favoriteState(state: Boolean)
    fun errorFavorite(message: CharSequence)
}