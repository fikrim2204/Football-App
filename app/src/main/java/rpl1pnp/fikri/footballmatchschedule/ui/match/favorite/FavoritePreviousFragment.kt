package rpl1pnp.fikri.footballmatchschedule.ui.match.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import rpl1pnp.fikri.footballmatchschedule.R
import rpl1pnp.fikri.footballmatchschedule.adapter.FavoriteAdapter
import rpl1pnp.fikri.footballmatchschedule.database.Favorite
import rpl1pnp.fikri.footballmatchschedule.view.FavoriteView

class FavoritePreviousFragment : Fragment(), FavoriteView {
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
        previousFavList = rootView.findViewById(R.id.rv_fav_prev_match) as RecyclerView
        previousFavList.layoutManager = LinearLayoutManager(activity)
        adapter = FavoriteAdapter(favorites)

        previousFavList.adapter = adapter
        presenter = FavoritePresenter(this, requireActivity())
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.showFavoritePrev()

        super.onViewCreated(view, savedInstanceState)
    }

    override fun showFavorite(data: List<Favorite>) {
        favorites.clear()
        favorites.addAll(data)
        adapter.notifyDataSetChanged()
    }
}
