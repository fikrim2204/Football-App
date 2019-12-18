package rpl1pnp.fikri.footballmatchschedule.presenter

import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import rpl1pnp.fikri.footballmatchschedule.model.LeagueDetailResponse
import rpl1pnp.fikri.footballmatchschedule.network.ApiRepositori
import rpl1pnp.fikri.footballmatchschedule.network.TheSportDBApi
import rpl1pnp.fikri.footballmatchschedule.view.LeagueView

class LeaguePresenter(
    private val view: LeagueView,
    private val apiRepositori: ApiRepositori,
    private val gson: Gson
) {
    fun getLeagueList(league: String?) {
        view.showLoading()
        doAsync {
            val data = gson.fromJson(
                apiRepositori.doRequest(
                    TheSportDBApi.getLeagueDetail(
                        league
                    )
                ),
                LeagueDetailResponse::class.java
            )

            uiThread {
                view.hideLoading()
                view.showLeagueList(data.leagues)
            }
        }
    }
}