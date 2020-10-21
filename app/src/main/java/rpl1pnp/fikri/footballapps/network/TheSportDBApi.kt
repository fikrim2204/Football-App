package rpl1pnp.fikri.footballapps.network

import rpl1pnp.fikri.footballapps.network.Constant.BASE_URL
import rpl1pnp.fikri.footballapps.network.Constant.LASTMATCH
import rpl1pnp.fikri.footballapps.network.Constant.LOOKUPALLTEAMS
import rpl1pnp.fikri.footballapps.network.Constant.LOOKUPEVENT
import rpl1pnp.fikri.footballapps.network.Constant.LOOKUPLEAGUE
import rpl1pnp.fikri.footballapps.network.Constant.LOOKUPTABLE
import rpl1pnp.fikri.footballapps.network.Constant.LOOKUPTEAMS
import rpl1pnp.fikri.footballapps.network.Constant.NEXTMATCH
import rpl1pnp.fikri.footballapps.network.Constant.PATH
import rpl1pnp.fikri.footballapps.network.Constant.SEARCHMATCH
import rpl1pnp.fikri.footballapps.network.Constant.SEARCHTEAM

object TheSportDBApi {
    fun getLeagueDetail(idLeague: String?): String {
        return BASE_URL + PATH + LOOKUPLEAGUE + idLeague
    }

    fun getNextMatch(idLeague: String?): String {
        return BASE_URL + PATH + NEXTMATCH + idLeague
    }

    fun getLastMatch(idLeague: String?): String {
        return BASE_URL + PATH + LASTMATCH + idLeague
    }

    fun getDetailMatch(idEvent: String?): String {
        return BASE_URL + PATH + LOOKUPEVENT + idEvent
    }

    fun getSearchMatch(query: String?): String {
        return BASE_URL + PATH + SEARCHMATCH + query
    }

    fun getSearchTeam(query: String?): String {
        return BASE_URL + PATH + SEARCHTEAM + query
    }

    fun getTeams(teams: String?): String {
        return BASE_URL + PATH + LOOKUPTEAMS + teams
    }

    fun getAllTeams(idLeague: String?): String {
        return BASE_URL + PATH + LOOKUPALLTEAMS + idLeague
    }

    fun getTable(idLeague: String?): String {
        return BASE_URL + PATH + LOOKUPTABLE + idLeague
    }
}