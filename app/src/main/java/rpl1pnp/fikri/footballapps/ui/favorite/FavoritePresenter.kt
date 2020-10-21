package rpl1pnp.fikri.footballapps.ui.favorite

import android.content.Context
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import rpl1pnp.fikri.footballapps.database.FavoriteMatch
import rpl1pnp.fikri.footballapps.database.database
import rpl1pnp.fikri.footballapps.view.FavoriteView

class FavoritePresenter(private val view: FavoriteView, private val context: Context) {
    private var favoriteMatches: MutableList<FavoriteMatch> = mutableListOf()

    fun showFavoritePrev() {
        favoriteMatches.clear()
        context.database.use {
            val result =
                select(FavoriteMatch.TABLE_FAVORITE_MATCH).whereArgs("(HOME_SCORE IS NOT NULL) and (AWAY_SCORE IS NOT NULL)")
            val favorite = result.parseList(classParser<FavoriteMatch>())
            view.showFavorite(favorite)
        }
    }

    fun showFavoriteNext() {
        favoriteMatches.clear()
        context.database.use {
            val result =
                select(FavoriteMatch.TABLE_FAVORITE_MATCH).whereArgs("(HOME_SCORE IS NULL) and (AWAY_SCORE IS NULL)")
            val favorite = result.parseList(classParser<FavoriteMatch>())
            view.showFavorite(favorite)
        }
    }
}