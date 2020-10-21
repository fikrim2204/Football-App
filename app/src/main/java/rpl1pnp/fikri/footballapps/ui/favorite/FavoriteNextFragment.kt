package rpl1pnp.fikri.footballapps.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_favorite_next.*
import rpl1pnp.fikri.footballapps.R
import rpl1pnp.fikri.footballapps.adapter.FavoriteMatchAdapter
import rpl1pnp.fikri.footballapps.database.FavoriteMatch
import rpl1pnp.fikri.footballapps.util.invisible
import rpl1pnp.fikri.footballapps.util.visible
import rpl1pnp.fikri.footballapps.view.FavoriteView

class FavoriteNextFragment : Fragment(), FavoriteView {
    lateinit var matchAdapter: FavoriteMatchAdapter
    private var favoriteMatches: MutableList<FavoriteMatch> = mutableListOf()
    private lateinit var nextFavList: RecyclerView
    private lateinit var presenter: FavoritePresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView: View = inflater.inflate(R.layout.fragment_favorite_next, container, false)
        nextFavList = rootView.findViewById(R.id.rv_fav_next_match) as RecyclerView
        nextFavList.layoutManager = LinearLayoutManager(activity)
        matchAdapter = FavoriteMatchAdapter(favoriteMatches)

        nextFavList.adapter = matchAdapter
        presenter = FavoritePresenter(this, requireActivity())
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.showFavoriteNext()
        if (favoriteMatches.isEmpty()) {
            null_data_next_fav.visible()
        } else {
            null_data_next_fav.invisible()
        }

        super.onViewCreated(view, savedInstanceState)
    }

    override fun showFavorite(data: List<FavoriteMatch>) {
        favoriteMatches.clear()
        favoriteMatches.addAll(data)
        matchAdapter.notifyDataSetChanged()
    }


}