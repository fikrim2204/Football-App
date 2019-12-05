package rpl1pnp.fikri.footballmatchschedule.presenter

import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import rpl1pnp.fikri.footballmatchschedule.model.TeamResponse
import rpl1pnp.fikri.footballmatchschedule.network.ApiRepositori
import rpl1pnp.fikri.footballmatchschedule.network.TheSportDBApi
import rpl1pnp.fikri.footballmatchschedule.view.MainView

class MainPresenter(private val view: MainView,
                    private val apiRepositori: ApiRepositori,
                    private val gson: Gson){
    fun getTeamList(league: String?) {
        view.showLoading()
        doAsync {
                val data = gson.fromJson(
                    apiRepositori.doRequest(
                        TheSportDBApi.getTeams(
                            league
                        )
                    ),
                    TeamResponse::class.java
                )

            uiThread {
                view.hideLoading()
                view.showTeamList(data.teams)
            }
        }
    }
}