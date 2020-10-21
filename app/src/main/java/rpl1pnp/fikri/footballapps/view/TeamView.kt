package rpl1pnp.fikri.footballapps.view

import rpl1pnp.fikri.footballapps.model.Team

interface TeamView {
    fun hideLoading()
    fun showLoading()
    fun getListTeam(data: List<Team>)
}