package rpl1pnp.fikri.footballmatchschedule.network

import rpl1pnp.fikri.footballmatchschedule.network.Constant.BASE_URL
import rpl1pnp.fikri.footballmatchschedule.network.Constant.LOOKUPEVENT
import rpl1pnp.fikri.footballmatchschedule.network.Constant.LOOKUPLEAGUE
import rpl1pnp.fikri.footballmatchschedule.network.Constant.LOOKUPTEAMS
import rpl1pnp.fikri.footballmatchschedule.network.Constant.NEXTMATCH
import rpl1pnp.fikri.footballmatchschedule.network.Constant.PATH
import rpl1pnp.fikri.footballmatchschedule.network.Constant.PREVIOUSMATCH
import rpl1pnp.fikri.footballmatchschedule.network.Constant.SEARCH

object TheSportDBApi {
    fun getLeagueDetail(idLeague: String?): String {
        return BASE_URL + PATH + LOOKUPLEAGUE + idLeague
    }

    fun getNextMatch(idLeague: String?): String {
        return BASE_URL + PATH + NEXTMATCH + idLeague
    }

    fun getPreviousMatch(idLeague: String?): String {
        return BASE_URL + PATH + PREVIOUSMATCH + idLeague
    }

    fun getDetailMatch(idEvent: String?): String {
        return BASE_URL + PATH + LOOKUPEVENT + idEvent
    }

    fun getSearch(query: String?): String {
        return BASE_URL + PATH + SEARCH + query
    }

    fun getTeams(teams: String?): String {
        return BASE_URL + PATH + LOOKUPTEAMS + teams
    }
}