package rpl1pnp.fikri.footballapps.ui.team

import com.google.gson.Gson
import rpl1pnp.fikri.footballapps.model.TeamResponse
import rpl1pnp.fikri.footballapps.network.ApiRepository
import rpl1pnp.fikri.footballapps.network.TheSportDBApi
import rpl1pnp.fikri.footballapps.view.TeamView

class TeamPresenter(
    private val view: TeamView,
    private val apiRepository: ApiRepository,
    private val gson: Gson
) {
    suspend fun getListTeam(idLeague: String?) {
        view.showLoading()

        val data = gson.fromJson(
            apiRepository.doRequestAsync(TheSportDBApi.getAllTeams(idLeague)).await(),
            TeamResponse::class.java
        )

        view.hideLoading()
        view.getListTeam(data.teams)
    }
}