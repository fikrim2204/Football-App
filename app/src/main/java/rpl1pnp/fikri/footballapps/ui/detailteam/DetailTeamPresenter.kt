package rpl1pnp.fikri.footballapps.ui.detailteam

import com.google.gson.Gson
import rpl1pnp.fikri.footballapps.model.TeamResponse
import rpl1pnp.fikri.footballapps.network.ApiRepository
import rpl1pnp.fikri.footballapps.network.TheSportDBApi
import rpl1pnp.fikri.footballapps.view.DetailTeamView

class DetailTeamPresenter(
    private val view: DetailTeamView,
    private val apiRepository: ApiRepository,
    private val gson: Gson
) {
    suspend fun getDetailTeam(idTeam: String?) {
        view.showLoading()

        val data = gson.fromJson(
            apiRepository.doRequestAsync(TheSportDBApi.getTeams(idTeam)).await(),
            TeamResponse::class.java
        )

        view.hideLoading()
        view.showDetail(data.teams)
    }
}