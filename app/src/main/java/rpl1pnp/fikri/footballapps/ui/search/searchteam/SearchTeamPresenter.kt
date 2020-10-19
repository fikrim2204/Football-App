package rpl1pnp.fikri.footballapps.ui.search.searchteam

import com.google.gson.Gson
import rpl1pnp.fikri.footballapps.model.SearchTeamResponse
import rpl1pnp.fikri.footballapps.model.Teams
import rpl1pnp.fikri.footballapps.network.ApiRepository
import rpl1pnp.fikri.footballapps.network.TheSportDBApi
import rpl1pnp.fikri.footballapps.view.SearchTeamView

class SearchTeamPresenter(
    private val searchTeamView: SearchTeamView,
    private val request: ApiRepository,
    private val gson: Gson
) {
    private var teams: MutableList<Teams> = mutableListOf()

    suspend fun getSearchTeam(query: String?) {
        searchTeamView.showLoading()

        val data = gson.fromJson(
            request.doRequestAsync(TheSportDBApi.getSearchTeam(query)).await(),
            SearchTeamResponse::class.java
        )

        if (data.teams.isNullOrEmpty()) {
            teams.clear()
            searchTeamView.hideLoading()
            searchTeamView.nullData()
        } else {
            teams.clear()
            for (element in data.teams) {
                if (element.strSport == "Soccer") {
                    teams.add(element)
                    searchTeamView.hideLoading()
                    searchTeamView.searchTeam(teams)
                }
            }
        }
    }
}