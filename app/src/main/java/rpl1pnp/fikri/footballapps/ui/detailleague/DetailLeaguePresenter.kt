package rpl1pnp.fikri.footballapps.ui.detailleague

import com.google.gson.Gson
import rpl1pnp.fikri.footballapps.model.LeagueDetailResponse
import rpl1pnp.fikri.footballapps.network.ApiRepository
import rpl1pnp.fikri.footballapps.network.TheSportDBApi
import rpl1pnp.fikri.footballapps.view.DetailLeagueView

class DetailLeaguePresenter(
    private val view: DetailLeagueView,
    private val apiRepository: ApiRepository,
    private val gson: Gson
) {
    suspend fun getLeagueList(league: String?) {
        view.showLoading()

        val data = gson.fromJson(
            apiRepository.doRequestAsync(
                TheSportDBApi.getLeagueDetail(
                    league
                )
            ).await(),
            LeagueDetailResponse::class.java
        )

        view.hideLoading()
        view.showLeagueList(data.leagues)
    }
}