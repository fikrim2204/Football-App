package rpl1pnp.fikri.footballmatchschedule.ui.league

import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import rpl1pnp.fikri.footballmatchschedule.model.LeagueDetailResponse
import rpl1pnp.fikri.footballmatchschedule.network.ApiRepository
import rpl1pnp.fikri.footballmatchschedule.network.TheSportDBApi
import rpl1pnp.fikri.footballmatchschedule.util.CoroutineContextProvider
import rpl1pnp.fikri.footballmatchschedule.view.LeagueView

class LeaguePresenter(
    private val view: LeagueView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {
    fun getLeagueList(league: String?) {
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository.doRequest(
                    TheSportDBApi.getLeagueDetail(
                        league
                    )
                ).await(),
                LeagueDetailResponse::class.java
            )

            view.showLeagueList(data.leagues)
        }
    }
}
