package rpl1pnp.fikri.footballmatchschedule.view

import rpl1pnp.fikri.footballmatchschedule.model.LeagueDetail

interface MatchView {
    fun showLoading()
    fun hideLoading()
    fun showLeagueList(data: LeagueDetail)
}