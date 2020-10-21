package rpl1pnp.fikri.footballapps.ui.favorite

import android.content.Context
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import rpl1pnp.fikri.footballapps.database.FavoriteTeam
import rpl1pnp.fikri.footballapps.database.database
import rpl1pnp.fikri.footballapps.view.FavoriteTeamView

class FavoriteTeamPresenter(private val teamsView: FavoriteTeamView, private val context: Context) {
    private var favoriteTeam: MutableList<FavoriteTeam> = mutableListOf()

    fun showFavoriteTeam() {
        favoriteTeam.clear()
        context.database.use {
            val result = select(FavoriteTeam.TABLE_FAVORITE_TEAM)
            val favorite = result.parseList(classParser<FavoriteTeam>())
            teamsView.showFavoriteTeam(favorite)
        }
    }
}