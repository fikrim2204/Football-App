package rpl1pnp.fikri.footballmatchschedule.ui.match.nextmatch


import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.fragment_next_match.*
import org.jetbrains.anko.singleTop
import org.jetbrains.anko.support.v4.intentFor
import org.jetbrains.anko.support.v4.onRefresh
import rpl1pnp.fikri.footballmatchschedule.R
import rpl1pnp.fikri.footballmatchschedule.adapter.EventsAdapter
import rpl1pnp.fikri.footballmatchschedule.model.Events
import rpl1pnp.fikri.footballmatchschedule.ui.match.DetailMatchActivity
import rpl1pnp.fikri.footballmatchschedule.ui.match.favorite.FavoriteActivity
import rpl1pnp.fikri.footballmatchschedule.ui.viewpager.PageViewModel
import rpl1pnp.fikri.footballmatchschedule.util.invisible
import rpl1pnp.fikri.footballmatchschedule.util.visible

/**
 * A simple [Fragment] subclass.
 */
class NextMatchFragment : Fragment() {
    private lateinit var viewModel: PageViewModel
    private lateinit var viewModelNext: NextMatchViewModel
    private var events: MutableList<Events> = mutableListOf()
    private lateinit var nextList: RecyclerView
    private lateinit var adapter: EventsAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private var idLeague: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        viewModel =
            ViewModelProvider(requireActivity()).get(PageViewModel::class.java)
        viewModelNext = ViewModelProvider(this).get(NextMatchViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView: View = inflater.inflate(R.layout.fragment_next_match, container, false)
        nextList = rootView.findViewById(R.id.rv_next_match) as RecyclerView
        nextList.layoutManager = LinearLayoutManager(activity)
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

        nextList.adapter = adapter
        swipeRefreshLayout =
            rootView.findViewById(R.id.srl_next) as SwipeRefreshLayout

        idLeague = viewModel.getIdLeague()
        viewModelNext.loadData(idLeague)
        viewModelNext.observeNextMatch().observe(viewLifecycleOwner,
            {
                if (it != null) {
                    events.clear()
                    events.addAll(it.events)
                    adapter.notifyDataSetChanged()
                    null_data_next.visibility = View.GONE
                }
            })
        viewModelNext.observeLoading().observe(viewLifecycleOwner,
            {
                progressBar(it)
            })

        swipeRefreshLayout.onRefresh {
            viewModelNext.loadData(idLeague)
            swipeRefreshLayout.isRefreshing = false
        }

        return rootView
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorite -> {
                startActivity(intentFor<FavoriteActivity>().singleTop())
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val menuItem = menu.findItem(R.id.searchMatch)
        val search = menuItem?.actionView as SearchView
        searching(search)

        super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.menuInflater?.inflate(R.menu.navigation, menu)
        val menuItem = menu.findItem(R.id.searchMatch)
        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.searchMatch).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searching(searchView)

        menuItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(p0: MenuItem?): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
                viewModelNext.loadData(idLeague)
                return true
            }

        })
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun searching(searchView: SearchView) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query == null) {
                    return false
                }
                viewModelNext.getSearch(query, idLeague)
                viewModelNext.observeSearch().observe(viewLifecycleOwner,
                    {
                        if (it != null) {
                            events.clear()
                            events.addAll(it.event)
                            adapter.notifyDataSetChanged()
                            null_data_next.visibility = View.GONE
                        } else {
                            events.clear()
                            null_data_next.visibility = View.VISIBLE
                        }
                    })
                return true
            }
        })
    }

    private fun progressBar(isTrue: Boolean) {
        if (isTrue) {
            return progressbar_next.visible()
        }
        return progressbar_next.invisible()
    }
}
