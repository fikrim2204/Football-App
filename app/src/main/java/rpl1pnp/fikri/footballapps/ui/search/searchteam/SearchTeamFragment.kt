package rpl1pnp.fikri.footballapps.ui.search.searchteam

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_search_team.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.jetbrains.anko.support.v4.onRefresh
import rpl1pnp.fikri.footballapps.R
import rpl1pnp.fikri.footballapps.adapter.TeamAdapter
import rpl1pnp.fikri.footballapps.model.Team
import rpl1pnp.fikri.footballapps.network.ApiRepository
import rpl1pnp.fikri.footballapps.util.CoroutineContextProvider
import rpl1pnp.fikri.footballapps.util.invisible
import rpl1pnp.fikri.footballapps.util.visible
import rpl1pnp.fikri.footballapps.view.SearchTeamView
import kotlin.coroutines.CoroutineContext

class SearchTeamFragment : Fragment(), SearchTeamView, CoroutineScope {
    private lateinit var adapter: TeamAdapter
    private lateinit var presenter: SearchTeamPresenter
    private var teams: MutableList<Team> = mutableListOf()
    private lateinit var job: Job
    private val coroutineContextProvider = CoroutineContextProvider()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        job = Job()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_team, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerView()
        setPresenter()
        setSwipeRefreshLayout()
    }

    private fun setSwipeRefreshLayout() {
        srl_search_team?.onRefresh {
            launch { presenter.getSearchTeam("liverpool") }
            teams.clear()
            adapter.notifyDataSetChanged()
            srl_search_team?.isRefreshing = false
        }
    }

    private fun setPresenter() {
        val request = ApiRepository()
        val gson = Gson()
        presenter = SearchTeamPresenter(this, request, gson)
    }

    private fun setRecyclerView() {
        rv_search_team?.layoutManager = LinearLayoutManager(activity)
        adapter = TeamAdapter(teams) {}
        rv_search_team?.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.menuInflater?.inflate(R.menu.search_menu, menu)
        val menuItem = menu.findItem(R.id.btn_search)
        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.btn_search).actionView as SearchView

        val imm =
            this.context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm!!.showSoftInput(searchView, InputMethodManager.SHOW_IMPLICIT)

        searchView.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchMatch(searchView)

        menuItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(p0: MenuItem?): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
                teams.clear()
                adapter.notifyDataSetChanged()
                return true
            }

        })
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun searchMatch(searchView: SearchView) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(query: String?): Boolean {
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query == null) {
                    return false
                }
                launch { presenter.getSearchTeam(query) }
                searchView.clearFocus()

                return true
            }
        })

    }

    override fun showLoading() {
        progressbar_search_team.visible()
    }

    override fun hideLoading() {
        progressbar_search_team.invisible()
    }

    override fun searchTeam(data: List<Team>) {
        teams.clear()
        teams.addAll(data)
        adapter.notifyDataSetChanged()
        null_data_search_team.invisible()
    }

    override fun nullData() {
        teams.clear()
        adapter.notifyDataSetChanged()
        null_data_search_team.visible()
    }

    override val coroutineContext: CoroutineContext
        get() = job + coroutineContextProvider.main

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}