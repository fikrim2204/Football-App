package rpl1pnp.fikri.footballmatchschedule.view

import rpl1pnp.fikri.footballmatchschedule.model.LeagueDetail

interface DetailLeagueView {
    fun showLoading()
    fun hideLoading()
    fun showLeagueList(data: List<LeagueDetail>)
}