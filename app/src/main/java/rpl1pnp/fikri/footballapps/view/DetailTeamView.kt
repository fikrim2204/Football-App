package rpl1pnp.fikri.footballapps.view

import rpl1pnp.fikri.footballapps.model.Team

interface DetailTeamView {
    fun showLoading()
    fun hideLoading()
    fun showDetail(data: List<Team>)
    fun addFavorite()
    fun removeFavorite()
    fun favoriteState(state: Boolean)
    fun errorFavorite(message: CharSequence?)
}