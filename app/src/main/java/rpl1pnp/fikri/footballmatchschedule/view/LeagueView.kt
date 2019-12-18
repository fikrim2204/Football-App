package rpl1pnp.fikri.footballmatchschedule.view

import rpl1pnp.fikri.footballmatchschedule.model.LeagueDetail

interface LeagueView {
    fun showLoading()
    fun hideLoading()
    fun showLeagueList(data: List<LeagueDetail>)
}