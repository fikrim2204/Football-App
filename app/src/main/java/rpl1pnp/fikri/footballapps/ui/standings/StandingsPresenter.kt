package rpl1pnp.fikri.footballapps.ui.standings

import com.google.gson.Gson
import rpl1pnp.fikri.footballapps.model.TableResponse
import rpl1pnp.fikri.footballapps.network.ApiRepository
import rpl1pnp.fikri.footballapps.network.TheSportDBApi
import rpl1pnp.fikri.footballapps.view.StandingView

class StandingsPresenter(
    private val view: StandingView,
    private val apiRepository: ApiRepository,
    private val gson: Gson
) {
    suspend fun getTable(idLeague: String?) {
        view.showLoading()

        val data =
            gson.fromJson(
                apiRepository.doRequestAsync(TheSportDBApi.getTable(idLeague)).await(),
                TableResponse::class.java
            )

        view.hideLoading()
        view.getTable(data.table)
    }
}