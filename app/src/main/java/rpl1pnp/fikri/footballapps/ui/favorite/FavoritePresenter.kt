package rpl1pnp.fikri.footballapps.ui.favorite

import android.content.Context
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import rpl1pnp.fikri.footballapps.database.Favorite
import rpl1pnp.fikri.footballapps.database.database
import rpl1pnp.fikri.footballapps.view.FavoriteView

class FavoritePresenter(private val view: FavoriteView, private val context: Context) {
    private var favorites: MutableList<Favorite> = mutableListOf()

    fun showFavoritePrev() {
        favorites.clear()
        context.database.use {
            val result =
                select(Favorite.TABLE_FAVORITE).whereArgs("(HOME_SCORE IS NOT NULL) and (AWAY_SCORE IS NOT NULL)")
            val favorite = result.parseList(classParser<Favorite>())
            view.showFavorite(favorite)
        }
    }

    fun showFavoriteNext() {
        favorites.clear()
        context.database.use {
            val result =
                select(Favorite.TABLE_FAVORITE).whereArgs("(HOME_SCORE IS NULL) and (AWAY_SCORE IS NULL)")
            val favorite = result.parseList(classParser<Favorite>())
            view.showFavorite(favorite)
        }
    }
}