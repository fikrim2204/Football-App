package rpl1pnp.fikri.footballapps.view

import rpl1pnp.fikri.footballapps.model.Team

interface SearchTeamView {
    fun showLoading()
    fun hideLoading()
    fun searchTeam(data: List<Team>)
    fun nullData()
}