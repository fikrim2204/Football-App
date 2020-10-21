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
import rpl1pnp.fikri.footballapps.database.FavoriteMatch
import rpl1pnp.fikri.footballapps.database.database
import rpl1pnp.fikri.footballapps.model.Event
import rpl1pnp.fikri.footballapps.model.EventResponse
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
                EventResponse::class.java
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

    fun addToFavorite(context: Context, events: List<Event>?) {
        try {
            context.database.use {
                insert(
                    FavoriteMatch.TABLE_FAVORITE_MATCH,
                    FavoriteMatch.ID_EVENT to events?.first()?.eventId,
                    FavoriteMatch.ID_LEAGUE to events?.first()?.idLeague,
                    FavoriteMatch.ID_HOME_TEAM to events?.first()?.homeTeamId,
                    FavoriteMatch.ID_AWAY_TEAM to events?.first()?.awayTeamId,
                    FavoriteMatch.HOME_TEAM to events?.first()?.homeTeam,
                    FavoriteMatch.AWAY_TEAM to events?.first()?.awayTeam,
                    FavoriteMatch.HOME_SCORE to events?.first()?.homeScore,
                    FavoriteMatch.AWAY_SCORE to events?.first()?.awayScore,
                    FavoriteMatch.DATE_EVENT to events?.first()?.dateEvent,
                    FavoriteMatch.TIME to events?.first()?.time
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
                    FavoriteMatch.TABLE_FAVORITE_MATCH, "(ID_EVENT = {id})",
                    "id" to eventId.toString()
                )
            }
            view.removeFavorite()
        } catch (e: SQLiteConstraintException) {

        }
    }

    fun favoriteState(context: Context, eventId: String?) {
        context.database.use {
            val result = select(FavoriteMatch.TABLE_FAVORITE_MATCH)
                .whereArgs(
                    "(ID_EVENT = {id})",
                    "id" to eventId.toString()
                )
            val favorite = result.parseList(classParser<FavoriteMatch>())
            if (favorite.isNotEmpty()) {
                view.favoriteState(true)
            } else {
                view.favoriteState(false)
            }
        }
    }
}