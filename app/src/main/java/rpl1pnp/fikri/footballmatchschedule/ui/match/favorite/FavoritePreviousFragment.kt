package rpl1pnp.fikri.footballmatchschedule.ui.match.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import rpl1pnp.fikri.footballmatchschedule.R
import rpl1pnp.fikri.footballmatchschedule.adapter.FavoriteAdapter
import rpl1pnp.fikri.footballmatchschedule.database.Favorite
import rpl1pnp.fikri.footballmatchschedule.database.database

class FavoritePreviousFragment : Fragment() {
    lateinit var adapter: FavoriteAdapter
    private var favorites: MutableList<Favorite> = mutableListOf()
    private lateinit var previousFavList: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView: View = inflater.inflate(R.layout.fragment_favorite_previous, container, false)
        previousFavList = rootView.findViewById(R.id.rv_fav_prev_match) as RecyclerView
        previousFavList.layoutManager = LinearLayoutManager(activity)
        adapter = FavoriteAdapter(favorites) {
        }

        previousFavList.adapter = adapter
        showFavorite()
        return rootView
    }


    fun showFavorite() {
        favorites.clear()
        context?.database?.use {
            val result =
                select(Favorite.TABLE_FAVORITE).whereArgs("(HOME_SCORE IS NOT NULL) and (AWAY_SCORE IS NOT NULL)")
            val favorite = result.parseList(classParser<Favorite>())
            favorites.addAll(favorite)
            adapter.notifyDataSetChanged()
        }
    }
}
