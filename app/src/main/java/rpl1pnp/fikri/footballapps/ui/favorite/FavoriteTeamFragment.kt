package rpl1pnp.fikri.footballapps.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_favorite_team.*
import rpl1pnp.fikri.footballapps.R
import rpl1pnp.fikri.footballapps.adapter.FavoriteTeamAdapter
import rpl1pnp.fikri.footballapps.database.FavoriteTeam
import rpl1pnp.fikri.footballapps.util.invisible
import rpl1pnp.fikri.footballapps.util.visible
import rpl1pnp.fikri.footballapps.view.FavoriteTeamView

class FavoriteTeamFragment : Fragment(), FavoriteTeamView {
    private lateinit var adapter: FavoriteTeamAdapter
    private lateinit var presenter: FavoriteTeamPresenter
    private var favoriteTeams: MutableList<FavoriteTeam> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_team, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView()
        getDataFromDatabase()
        checkDataNotNull()
    }

    private fun getDataFromDatabase() {
        presenter = FavoriteTeamPresenter(this, requireActivity())
        presenter.showFavoriteTeam()
    }

    private fun recyclerView() {
        rv_fav_team?.layoutManager = LinearLayoutManager(requireActivity())
        adapter = FavoriteTeamAdapter(favoriteTeams)
        rv_fav_team?.adapter = adapter
    }

    override fun showFavoriteTeam(data: List<FavoriteTeam>) {
        favoriteTeams.clear()
        favoriteTeams.addAll(data)
        adapter.notifyDataSetChanged()
    }

    private fun checkDataNotNull() {
        if (favoriteTeams.isEmpty()) {
            null_data_team_fav.visible()
        } else {
            null_data_team_fav.invisible()
        }
    }
}