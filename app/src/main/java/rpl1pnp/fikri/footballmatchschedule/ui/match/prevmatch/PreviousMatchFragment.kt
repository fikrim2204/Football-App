package rpl1pnp.fikri.footballmatchschedule.ui.match.prevmatch


import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_previous_match.*
import org.jetbrains.anko.singleTop
import org.jetbrains.anko.support.v4.intentFor
import org.jetbrains.anko.support.v4.onRefresh
import rpl1pnp.fikri.footballmatchschedule.R
import rpl1pnp.fikri.footballmatchschedule.adapter.EventsAdapter
import rpl1pnp.fikri.footballmatchschedule.model.Events
import rpl1pnp.fikri.footballmatchschedule.network.ApiRepository
import rpl1pnp.fikri.footballmatchschedule.ui.match.detailmatch.DetailMatchActivity
import rpl1pnp.fikri.footballmatchschedule.ui.match.favorite.FavoriteActivity
import rpl1pnp.fikri.footballmatchschedule.ui.viewpager.PageViewModel
import rpl1pnp.fikri.footballmatchschedule.util.EspressoIdlingResource
import rpl1pnp.fikri.footballmatchschedule.util.invisible
import rpl1pnp.fikri.footballmatchschedule.util.visible
import rpl1pnp.fikri.footballmatchschedule.view.PreviousMatchView

/**
 * A simple [Fragment] subclass.
 */
class PreviousMatchFragment : Fragment(), PreviousMatchView {
    private lateinit var viewModel: PageViewModel
    private lateinit var presenter: PreviousMatchPresenter
    private var events: MutableList<Events> = mutableListOf()
    private lateinit var previousList: RecyclerView
    private lateinit var adapter: EventsAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    var idLeague: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        viewModel = ViewModelProvider(requireActivity()).get(PageViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView: View = inflater.inflate(R.layout.fragment_previous_match, container, false)
        previousList = rootView.findViewById(R.id.rv_previous_match) as RecyclerView
        previousList.layoutManager = LinearLayoutManager(activity)

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

        previousList.adapter = adapter
        swipeRefreshLayout =
            rootView.findViewById(R.id.srl_prev) as SwipeRefreshLayout

        idLeague = viewModel.getIdLeague()

        val request = ApiRepository()
        val gson = Gson()
        presenter = PreviousMatchPresenter(this, request, gson)

        swipeRefreshLayout.onRefresh {
            EspressoIdlingResource.increment()
            presenter.getListMatch(idLeague)
            swipeRefreshLayout.isRefreshing = false
        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        EspressoIdlingResource.increment()
        presenter.getListMatch(idLeague)

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.menuInflater?.inflate(R.menu.navigation, menu)
        val menuItem = menu.findItem(R.id.search_match)
        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search_match).actionView as SearchView

        val imm =
            this.context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm!!.showSoftInput(searchView, InputMethodManager.SHOW_IMPLICIT)

        searchView.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searching(searchView)

        menuItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(p0: MenuItem?): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
                EspressoIdlingResource.increment()
                presenter.getListMatch(idLeague)
                return true
            }

        })

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.btn_favorite -> {
                startActivity(intentFor<FavoriteActivity>().singleTop())
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun searching(searchView: SearchView) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(query: String?): Boolean {
                if (query!!.isEmpty()) {
                    presenter.getListMatch(idLeague)
                }
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query == null) {
                    return false
                }
                EspressoIdlingResource.increment()
                presenter.getSearchMatch(query)
                searchView.clearFocus()

                return true
            }
        })
    }

    override fun showLoading() {
        progressbar_prev.visible()
    }

    override fun hideLoading() {
        progressbar_prev.invisible()
    }

    override fun showListMatch(data: List<Events>) {
        if (!EspressoIdlingResource.idlingresource.isIdleNow) {
            EspressoIdlingResource.decrement()
        }
        events.clear()
        events.addAll(data)
        adapter.notifyDataSetChanged()
        null_data_prev.invisible()
    }

    override fun searchMatch(data: List<Events>) {
        if (!EspressoIdlingResource.idlingresource.isIdleNow) {
            EspressoIdlingResource.decrement()
        } else {
            events.clear()
            events.addAll(data)
            adapter.notifyDataSetChanged()
            null_data_prev.invisible()
        }
    }

    override fun nullData() {
        events.clear()
        adapter.notifyDataSetChanged()
        null_data_prev.visible()
    }
}


