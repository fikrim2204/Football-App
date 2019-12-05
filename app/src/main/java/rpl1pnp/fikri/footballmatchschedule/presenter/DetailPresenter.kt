package rpl1pnp.fikri.footballmatchschedule.presenter

import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import rpl1pnp.fikri.footballmatchschedule.model.EventsResponse
import rpl1pnp.fikri.footballmatchschedule.network.ApiRepositori
import rpl1pnp.fikri.footballmatchschedule.network.TheSportDBApi
import rpl1pnp.fikri.footballmatchschedule.view.DetailView

class DetailPresenter(private val view: DetailView, private val apiRepositori: ApiRepositori, private val gson: Gson) {
    fun getNextMatch() {
        view.showLoading()
         doAsync {
             var data = gson.fromJson(apiRepositori.doRequest(TheSportDBApi.getNextMatch()),
                 EventsResponse::class.java)

             uiThread {
                 view.hideLoading()
                 view.showNextMatch(data.events)
             }
         }
    }

    fun getPreviousMatch() {
        view.showLoading()
        doAsync {
            var data = gson.fromJson(apiRepositori.doRequest(TheSportDBApi.getPreviousMatch()),
                EventsResponse::class.java)

            uiThread {
                view.hideLoading()
                view.showPreviousMatch(data.events)
            }
        }
    }
}