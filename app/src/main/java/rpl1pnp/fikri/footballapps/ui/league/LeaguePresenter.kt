package rpl1pnp.fikri.footballapps.ui.league

import com.google.gson.Gson
import rpl1pnp.fikri.footballapps.model.LeagueDetailResponse
import rpl1pnp.fikri.footballapps.network.ApiRepository
import rpl1pnp.fikri.footballapps.network.TheSportDBApi
import rpl1pnp.fikri.footballapps.view.LeagueView

class LeaguePresenter(
    private val view: LeagueView,
    private val apiRepository: ApiRepository,
    private val gson: Gson
) {
    suspend fun getLeagueList(league: String?) {
        val data = gson.fromJson(
            apiRepository.doRequestAsync(
                TheSportDBApi.getLeagueDetail(
                    league
                )
            ).await(),
            LeagueDetailResponse::class.java
        )

        view.showLeagueList(data.leagues)
    }
}
