package rpl1pnp.fikri.footballapps.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_favorite_previous.*
import rpl1pnp.fikri.footballapps.R
import rpl1pnp.fikri.footballapps.adapter.FavoriteAdapter
import rpl1pnp.fikri.footballapps.database.Favorite
import rpl1pnp.fikri.footballapps.util.invisible
import rpl1pnp.fikri.footballapps.util.visible
import rpl1pnp.fikri.footballapps.view.FavoriteView

class FavoriteLastFragment : Fragment(), FavoriteView {
    lateinit var adapter: FavoriteAdapter
    private var favorites: MutableList<Favorite> = mutableListOf()
    private lateinit var previousFavList: RecyclerView
    private lateinit var presenter: FavoritePresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView: View = inflater.inflate(R.layout.fragment_favorite_previous, container, false)
        previousFavList = rootView.findViewById(R.id.rv_fav_last_match) as RecyclerView
        previousFavList.layoutManager = LinearLayoutManager(activity)
        adapter = FavoriteAdapter(favorites)

        previousFavList.adapter = adapter
        presenter = FavoritePresenter(this, requireActivity())
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.showFavoritePrev()
        if (favorites.isEmpty()) {
            null_data_prev_fav.visible()
        } else {
            null_data_prev_fav.invisible()
        }

        super.onViewCreated(view, savedInstanceState)
    }

    override fun showFavorite(data: List<Favorite>) {
        favorites.clear()
        favorites.addAll(data)
        adapter.notifyDataSetChanged()
    }
}
