package rpl1pnp.fikri.footballapps.ui.detailteam

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import com.google.gson.Gson
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import rpl1pnp.fikri.footballapps.database.FavoriteTeam
import rpl1pnp.fikri.footballapps.database.database
import rpl1pnp.fikri.footballapps.model.Team
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

    fun addToFavorite(context: Context, teams: List<Team>?) {
        try {
            context.database.use {
                insert(
                    FavoriteTeam.TABLE_FAVORITE_TEAM,
                    FavoriteTeam.ID_TEAM to teams?.first()?.idTeam,
                    FavoriteTeam.TEAM_BADGE to teams?.first()?.strTeamBadge,
                    FavoriteTeam.TEAM to teams?.first()?.strTeam
                )
            }
            view.addFavorite()
        } catch (e: SQLiteConstraintException) {
            view.errorFavorite(e.localizedMessage)
        }
    }

    fun removeFromFavorite(context: Context, teamId: String?) {
        try {
            context.database.use {
                delete(
                    FavoriteTeam.TABLE_FAVORITE_TEAM, "(ID_TEAM = {id})",
                    "id" to teamId.toString()
                )
            }
            view.removeFavorite()
        } catch (e: SQLiteConstraintException) {

        }
    }

    fun favoriteState(context: Context, teamId: String?) {
        context.database.use {
            val result = select(FavoriteTeam.TABLE_FAVORITE_TEAM)
                .whereArgs(
                    "(ID_TEAM = {id})",
                    "id" to teamId.toString()
                )
            val favorite = result.parseList(classParser<FavoriteTeam>())
            if (favorite.isNotEmpty()) {
                view.favoriteState(true)
            } else {
                view.favoriteState(false)
            }
        }
    }
}