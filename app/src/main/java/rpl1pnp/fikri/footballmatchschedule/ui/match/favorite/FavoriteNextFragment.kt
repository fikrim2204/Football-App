package rpl1pnp.fikri.footballmatchschedule.ui.match.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import rpl1pnp.fikri.footballmatchschedule.R
import rpl1pnp.fikri.footballmatchschedule.adapter.FavoriteAdapter
import rpl1pnp.fikri.footballmatchschedule.database.Favorite
import rpl1pnp.fikri.footballmatchschedule.database.database

class FavoriteNextFragment : Fragment() {
    lateinit var adapter: FavoriteAdapter
    private var favorites: MutableList<Favorite> = mutableListOf()
    private lateinit var nextFavList: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView: View = inflater.inflate(R.layout.fragment_favorite_next, container, false)
        nextFavList = rootView.findViewById(R.id.rv_fav_next_match) as RecyclerView
        nextFavList.layoutManager = LinearLayoutManager(activity)
        adapter = FavoriteAdapter(favorites)

        nextFavList.adapter = adapter
        showFavorite()
        return rootView
    }

    private fun showFavorite() {
        favorites.clear()
        context?.database?.use {
            val result =
                select(Favorite.TABLE_FAVORITE).whereArgs("(HOME_SCORE IS NULL) and (AWAY_SCORE IS NULL)")
            val favorite = result.parseList(classParser<Favorite>())
            favorites.addAll(favorite)
            adapter.notifyDataSetChanged()
        }
    }


}