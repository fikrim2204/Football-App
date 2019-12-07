package rpl1pnp.fikri.footballmatchschedule.view

import rpl1pnp.fikri.footballmatchschedule.model.DetailLeague

interface DetailLeagueView {
    fun showLoading()
    fun hideLoading()
    fun showDetailLeague(data: DetailLeague)
}