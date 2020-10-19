package rpl1pnp.fikri.footballapps.ui.search.searchmatch

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_search_match.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.jetbrains.anko.singleTop
import org.jetbrains.anko.support.v4.intentFor
import org.jetbrains.anko.support.v4.onRefresh
import rpl1pnp.fikri.footballapps.R
import rpl1pnp.fikri.footballapps.adapter.EventsAdapter
import rpl1pnp.fikri.footballapps.model.Events
import rpl1pnp.fikri.footballapps.network.ApiRepository
import rpl1pnp.fikri.footballapps.ui.detailmatch.DetailMatchActivity
import rpl1pnp.fikri.footballapps.util.CoroutineContextProvider
import rpl1pnp.fikri.footballapps.util.invisible
import rpl1pnp.fikri.footballapps.util.visible
import rpl1pnp.fikri.footballapps.view.SearchMatchView
import kotlin.coroutines.CoroutineContext

class SearchMatchFragment : Fragment(), SearchMatchView, CoroutineScope {
    private lateinit var adapter: EventsAdapter
    private lateinit var presenter: SearchMatchPresenter
    private lateinit var searchMatchList: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private var events: MutableList<Events> = mutableListOf()
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
        val rootView = inflater.inflate(R.layout.fragment_search_match, container, false)
        searchMatchList = rootView.findViewById(R.id.rv_search_match)
        searchMatchList.layoutManager = LinearLayoutManager(activity)

        adapter = EventsAdapter(events) {
            val idEvent = it.eventId.toString()
            val idHome = it.homeTeamId.toString()
            val idAway = it.awayTeamId.toString()
            startActivity(
                intentFor<DetailMatchActivity>(
                    "EVENT_ID" to idEvent,
                    "HOME_TEAM" to idHome,
                    "AWAY_TEAM" to idAway
                ).singleTop()
            )
        }

        searchMatchList.adapter = adapter
        val request = ApiRepository()
        val gson = Gson()
        presenter = SearchMatchPresenter(this, request, gson)

        swipeRefreshLayout = rootView.findViewById(R.id.srl_search_match)
        swipeRefreshLayout.onRefresh {
            launch { presenter.getSearchMatch("liverpool") }
            events.clear()
            adapter.notifyDataSetChanged()
            swipeRefreshLayout.isRefreshing = false
        }
        return rootView
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
                events.clear()
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
                launch { presenter.getSearchMatch(query) }
                searchView.clearFocus()

                return true
            }
        })
    }

    override fun showLoading() {
        progressbar_search_match.visible()
    }

    override fun hideLoading() {
        progressbar_search_match.invisible()
    }

    override fun searchMatch(data: List<Events>) {
        events.clear()
        events.addAll(data)
        adapter.notifyDataSetChanged()
        null_data_search_match.invisible()
    }

    override fun nullData() {
        events.clear()
        adapter.notifyDataSetChanged()
        null_data_search_match.visible()
    }

    override val coroutineContext: CoroutineContext
        get() = job + coroutineContextProvider.main

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}
