package rpl1pnp.fikri.footballmatchschedule.presenter

import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import rpl1pnp.fikri.footballmatchschedule.model.EventsResponse
import rpl1pnp.fikri.footballmatchschedule.network.ApiRepositori
import rpl1pnp.fikri.footballmatchschedule.network.TheSportDBApi
import rpl1pnp.fikri.footballmatchschedule.view.DetailView

class MatchPresenter(private val view: DetailView, private val apiRepositori: ApiRepositori, private val gson: Gson) {
    fun getNextMatch(idLeague: String?) {
        view.showLoading()
         doAsync {
             val data = gson.fromJson(apiRepositori.doRequest(TheSportDBApi.getNextMatch(idLeague)),
                 EventsResponse::class.java)

             uiThread {
                 view.hideLoading()
                 view.showNextMatch(data.events)
             }
         }
    }

    fun getPreviousMatch(idLeague: String?) {
        view.showLoading()
        doAsync {
            val data = gson.fromJson(apiRepositori.doRequest(TheSportDBApi.getPreviousMatch(idLeague)),
                EventsResponse::class.java)

            uiThread {
                view.hideLoading()
                view.showPreviousMatch(data.events)
            }
        }
    }
}