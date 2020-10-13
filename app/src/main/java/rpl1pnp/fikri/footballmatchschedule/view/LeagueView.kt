package rpl1pnp.fikri.footballmatchschedule.view

import rpl1pnp.fikri.footballmatchschedule.model.LeagueDetail

interface LeagueView {
    fun showLeagueList(data: List<LeagueDetail>)
}