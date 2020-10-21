package rpl1pnp.fikri.footballapps.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_favorite_next.*
import rpl1pnp.fikri.footballapps.R
import rpl1pnp.fikri.footballapps.adapter.FavoriteMatchAdapter
import rpl1pnp.fikri.footballapps.database.FavoriteMatch
import rpl1pnp.fikri.footballapps.util.invisible
import rpl1pnp.fikri.footballapps.util.visible
import rpl1pnp.fikri.footballapps.view.FavoriteMatchView

class FavoriteMatchNextFragment : Fragment(), FavoriteMatchView {
    private lateinit var matchAdapter: FavoriteMatchAdapter
    private var favoriteMatches: MutableList<FavoriteMatch> = mutableListOf()
    private lateinit var matchPresenter: FavoriteMatchPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_next, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView()
        getDataFromDatabase()
        checkDataNotNull()
    }

    private fun checkDataNotNull() {
        if (favoriteMatches.isEmpty()) {
            null_data_next_fav.visible()
        } else {
            null_data_next_fav.invisible()
        }
    }

    private fun getDataFromDatabase() {
        matchPresenter = FavoriteMatchPresenter(this, requireActivity())
        matchPresenter.showFavoriteNext()
    }

    private fun recyclerView() {
        rv_fav_next_match?.layoutManager = LinearLayoutManager(activity)
        matchAdapter = FavoriteMatchAdapter(favoriteMatches)
        rv_fav_next_match?.adapter = matchAdapter
    }

    override fun showFavoriteMatch(data: List<FavoriteMatch>) {
        favoriteMatches.clear()
        favoriteMatches.addAll(data)
        matchAdapter.notifyDataSetChanged()
    }
}