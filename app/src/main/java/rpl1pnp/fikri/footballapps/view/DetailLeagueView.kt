package rpl1pnp.fikri.footballapps.view

import rpl1pnp.fikri.footballapps.model.LeagueDetail

interface DetailLeagueView {
    fun showLoading()
    fun hideLoading()
    fun showLeagueList(data: List<LeagueDetail>)
}