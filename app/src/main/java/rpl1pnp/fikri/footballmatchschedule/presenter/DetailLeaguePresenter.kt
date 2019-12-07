package rpl1pnp.fikri.footballmatchschedule.presenter

import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import rpl1pnp.fikri.footballmatchschedule.model.DetailLeagueResponse
import rpl1pnp.fikri.footballmatchschedule.network.ApiRepositori
import rpl1pnp.fikri.footballmatchschedule.network.TheSportDBApi
import rpl1pnp.fikri.footballmatchschedule.view.DetailLeagueView

class DetailLeaguePresenter (private val view: DetailLeagueView,
    private val apiRepositori: ApiRepositori,
    private val gson: Gson) {
    fun getLeagueDetail(league: String?) {
        view.showLoading()
        doAsync {
            val data = gson.fromJson(
                apiRepositori.doRequest(
                    TheSportDBApi.getLeagueDetail(
                        league
                    )
                ),
                DetailLeagueResponse::class.java
            )

            uiThread {
                view.hideLoading()
                view.showDetailLeague(data.league.get(0))
            }
        }
    }
}