package rpl1pnp.fikri.footballapps.view

import rpl1pnp.fikri.footballapps.model.Teams

interface SearchTeamView {
    fun showLoading()
    fun hideLoading()
    fun searchTeam(data: List<Teams>)
    fun nullData()
}