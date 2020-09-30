package rpl1pnp.fikri.footballmatchschedule.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import rpl1pnp.fikri.footballmatchschedule.model.EventsResponse
import rpl1pnp.fikri.footballmatchschedule.model.LeagueDetailResponse
import rpl1pnp.fikri.footballmatchschedule.model.SearchResponse
import rpl1pnp.fikri.footballmatchschedule.model.TeamResponse

interface ApiInterface {
    @GET(Constant.PATH + Constant.LOOKUPLEAGUE)
    fun lookupLeague(
        @Query("id") id: String?
    ): Call<LeagueDetailResponse>


    @GET(Constant.PATH + Constant.NEXTMATCH)
    fun nextMatch(
        @Query("id") id: String?
    ): Call<EventsResponse>

    @GET(Constant.PATH + Constant.PREVIOUSMATCH)
    fun prevMatch(
        @Query("id") id: String?
    ): Call<EventsResponse>

    @GET(Constant.PATH + Constant.LOOKUPEVENT)
    fun detailMatch(
        @Query("id") id: String?
    ): Call<EventsResponse>

    @GET(Constant.PATH + Constant.SEARCH)
    fun search(
        @Query("e") id: String?
    ): Call<SearchResponse>

    @GET(Constant.PATH + Constant.LOOKUPTEAMS)
    fun getTeam(
        @Query("id") id: String?
    ): Call<TeamResponse>
}