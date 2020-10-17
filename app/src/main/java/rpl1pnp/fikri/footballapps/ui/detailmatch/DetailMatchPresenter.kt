package rpl1pnp.fikri.footballapps.ui.detailmatch

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import rpl1pnp.fikri.footballapps.database.Favorite
import rpl1pnp.fikri.footballapps.database.database
import rpl1pnp.fikri.footballapps.model.Events
import rpl1pnp.fikri.footballapps.model.EventsResponse
import rpl1pnp.fikri.footballapps.model.TeamResponse
import rpl1pnp.fikri.footballapps.network.ApiRepository
import rpl1pnp.fikri.footballapps.network.TheSportDBApi
import rpl1pnp.fikri.footballapps.util.CoroutineContextProvider
import rpl1pnp.fikri.footballapps.view.DetailMatchView

class DetailMatchPresenter(
    private val view: DetailMatchView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {
    fun getEvent(eventId: String?) {
        view.showLoading()

        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository.doRequestAsync(
                    TheSportDBApi.getDetailMatch(eventId)
                ).await(),
                EventsResponse::class.java
            )

            view.hideLoading()
            view.showDetail(data.events)
        }
    }

    fun getLogo(teamId: String?, isHomeTeam: Boolean) {
        view.showLoading()

        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository.doRequestAsync(
                    TheSportDBApi.getTeams(
                        teamId
                    )
                ).await(),
                TeamResponse::class.java
            )

            view.hideLoading()
            view.getLogoTeam(data.teams, isHomeTeam)
        }
    }

    fun addToFavorite(context: Context, events: List<Events>?) {
        try {
            context.database.use {
                insert(
                    Favorite.TABLE_FAVORITE,
                    Favorite.ID_EVENT to events?.first()?.eventId,
                    Favorite.ID_LEAGUE to events?.first()?.idLeague,
                    Favorite.ID_HOME_TEAM to events?.first()?.homeTeamId,
                    Favorite.ID_AWAY_TEAM to events?.first()?.awayTeamId,
                    Favorite.HOME_TEAM to events?.first()?.homeTeam,
                    Favorite.AWAY_TEAM to events?.first()?.awayTeam,
                    Favorite.HOME_SCORE to events?.first()?.homeScore,
                    Favorite.AWAY_SCORE to events?.first()?.awayScore,
                    Favorite.DATE_EVENT to events?.first()?.dateEvent,
                    Favorite.TIME to events?.first()?.time
                )
            }
            view.addFavorite()
        } catch (e: SQLiteConstraintException) {
            view.errorFavorite(e.localizedMessage)
        }
    }

    fun removeFromFavorite(context: Context, eventId: String?) {
        try {
            context.database.use {
                delete(
                    Favorite.TABLE_FAVORITE, "(ID_EVENT = {id})",
                    "id" to eventId.toString()
                )
            }
            view.removeFavorite()
        } catch (e: SQLiteConstraintException) {

        }
    }

    fun favoriteState(context: Context, eventId: String?) {
        context.database.use {
            val result = select(Favorite.TABLE_FAVORITE)
                .whereArgs(
                    "(ID_EVENT = {id})",
                    "id" to eventId.toString()
                )
            val favorite = result.parseList(classParser<Favorite>())
            if (favorite.isNotEmpty()) {
                view.favoriteState(true)
            } else {
                view.favoriteState(false)
            }
        }
    }
}