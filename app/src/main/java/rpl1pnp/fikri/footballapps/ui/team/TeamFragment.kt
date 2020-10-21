package rpl1pnp.fikri.footballapps.ui.team

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_team.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.jetbrains.anko.singleTop
import org.jetbrains.anko.support.v4.intentFor
import rpl1pnp.fikri.footballapps.R
import rpl1pnp.fikri.footballapps.adapter.TeamAdapter
import rpl1pnp.fikri.footballapps.model.Team
import rpl1pnp.fikri.footballapps.network.ApiRepository
import rpl1pnp.fikri.footballapps.ui.detailteam.DetailTeamActivity
import rpl1pnp.fikri.footballapps.ui.league.viewpager.PageViewModel
import rpl1pnp.fikri.footballapps.util.CoroutineContextProvider
import rpl1pnp.fikri.footballapps.util.invisible
import rpl1pnp.fikri.footballapps.util.visible
import rpl1pnp.fikri.footballapps.view.TeamView
import kotlin.coroutines.CoroutineContext

class TeamFragment : Fragment(), TeamView, CoroutineScope {
    private lateinit var adapter: TeamAdapter
    private lateinit var presenter: TeamPresenter
    private lateinit var viewModel: PageViewModel
    private var teams: MutableList<Team> = mutableListOf()
    private val coroutineContextProvider = CoroutineContextProvider()
    private lateinit var job: Job
    private var idLeague: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(PageViewModel::class.java)
        job = Job()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_team, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        idLeague = viewModel.getIdLeague()
        recylerView()
        getDataPresenter()
    }

    private fun getDataPresenter() {
        val request = ApiRepository()
        val gson = Gson()
        presenter = TeamPresenter(this, request, gson)
        launch { presenter.getListTeam(idLeague) }
    }

    private fun recylerView() {
        rv_list_team?.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        adapter = TeamAdapter(teams) {
            val idTeam = it.idTeam
            startActivity(intentFor<DetailTeamActivity>("ID_TEAM" to idTeam).singleTop())
        }

        rv_list_team?.adapter = adapter
    }

    override fun hideLoading() {
        pb_list_team?.invisible()
    }

    override fun showLoading() {
        pb_list_team?.visible()
    }

    override fun getListTeam(data: List<Team>) {
        teams.clear()
        teams.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override val coroutineContext: CoroutineContext
        get() = job + coroutineContextProvider.main

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

}