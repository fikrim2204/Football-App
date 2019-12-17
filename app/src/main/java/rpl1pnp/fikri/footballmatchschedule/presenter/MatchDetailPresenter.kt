package rpl1pnp.fikri.footballmatchschedule.presenter

import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import rpl1pnp.fikri.footballmatchschedule.model.EventsResponse
import rpl1pnp.fikri.footballmatchschedule.model.TeamResponse
import rpl1pnp.fikri.footballmatchschedule.network.ApiRepositori
import rpl1pnp.fikri.footballmatchschedule.network.TheSportDBApi
import rpl1pnp.fikri.footballmatchschedule.view.DetailMatchView

class MatchDetailPresenter(private val view: DetailMatchView,
                           private val apiRepositori: ApiRepositori,
                           private val gson: Gson
) {
    fun getEvent(eventId: String?) {
        view.showLoading()
        doAsync {
            val data = gson.fromJson(
                apiRepositori.doRequest(
                    TheSportDBApi.getDetailMatch(
                        eventId
                    )
                ),
                EventsResponse::class.java
            )

            uiThread {
                view.hideLoading()
                view.showDetailEvent(data.events.get(0))
            }
        }
    }

    fun getLogo(teamId: String?, isHomeTeam: Boolean=true) {
        view.showLoading()
        doAsync {
            val data = gson.fromJson(
                apiRepositori.doRequest(
                    TheSportDBApi.getDetailMatch(
                        teamId
                    )
                ),
                TeamResponse::class.java
            )

            uiThread {
                view.hideLoading()
                view.getLogoTeam(data.teams.get(0), isHomeTeam)
            }
        }
    }
}
