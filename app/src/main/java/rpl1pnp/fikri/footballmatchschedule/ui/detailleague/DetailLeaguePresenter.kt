package rpl1pnp.fikri.footballmatchschedule.ui.detailleague

import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import rpl1pnp.fikri.footballmatchschedule.model.LeagueDetailResponse
import rpl1pnp.fikri.footballmatchschedule.network.ApiRepository
import rpl1pnp.fikri.footballmatchschedule.network.TheSportDBApi
import rpl1pnp.fikri.footballmatchschedule.util.CoroutineContextProvider
import rpl1pnp.fikri.footballmatchschedule.view.DetailLeagueView

class DetailLeaguePresenter(
    private val view: DetailLeagueView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {
    fun getLeagueList(league: String?) {
        view.showLoading()

        GlobalScope.launch(context.main) {
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
}