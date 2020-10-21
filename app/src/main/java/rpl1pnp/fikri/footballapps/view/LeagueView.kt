package rpl1pnp.fikri.footballapps.view

import rpl1pnp.fikri.footballapps.model.LeagueDetail

interface LeagueView {
    fun showLeagueList(data: List<LeagueDetail>)
}