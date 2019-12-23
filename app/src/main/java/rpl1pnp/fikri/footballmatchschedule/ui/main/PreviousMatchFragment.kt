package rpl1pnp.fikri.footballmatchschedule.ui.main


import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.ProgressBar
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.uiThread
import rpl1pnp.fikri.footballmatchschedule.R
import rpl1pnp.fikri.footballmatchschedule.adapter.EventAdapter
import rpl1pnp.fikri.footballmatchschedule.model.Events
import rpl1pnp.fikri.footballmatchschedule.model.EventsResponse
import rpl1pnp.fikri.footballmatchschedule.network.ApiRepositori
import rpl1pnp.fikri.footballmatchschedule.network.TheSportDBApi
import rpl1pnp.fikri.footballmatchschedule.util.invisible
import rpl1pnp.fikri.footballmatchschedule.util.visible

/**
 * A simple [Fragment] subclass.
 */
class PreviousMatchFragment : Fragment() {
    private lateinit var viewModel: PageViewModel
    private lateinit var viewModelPrev: PreviousMatchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        viewModel = ViewModelProviders.of(requireActivity()).get(PageViewModel::class.java)
        viewModelPrev = ViewModelProviders.of(this).get(PreviousMatchViewModel::class.java)
    }

    private var events: MutableList<Events> = mutableListOf()
    private lateinit var previousList: RecyclerView
    private lateinit var adapter: EventAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    var idLeague: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView: View = inflater.inflate(R.layout.fragment_previous_match, container, false)
        previousList = rootView.findViewById(R.id.recycler_previous_match) as RecyclerView
        previousList.layoutManager = LinearLayoutManager(activity)

        adapter = EventAdapter(events) {
            val intent = Intent(activity, DetailMatchActivity::class.java)
            val idEvent = it.eventId.toString()
            val idHome = it.homeTeamId.toString()
            val idAway = it.awayTeamId.toString()
            intent.putExtra("EVENT_ID", idEvent)
            intent.putExtra("HOME_TEAM", idHome)
            intent.putExtra("AWAY_TEAM", idAway)
            startActivity(intent)
        }


        previousList.adapter = adapter
        progressBar = rootView.findViewById(R.id.progressBarPrev) as ProgressBar
        swipeRefreshLayout =
            rootView.findViewById(R.id.swipeRefreshLayoutPrev) as SwipeRefreshLayout

        idLeague = viewModel.getIdLeague()
        viewModelPrev.isLoading().observe(this,
            Observer {
                if (it == true) {
                    progressBar.visible()
                } else {
                    progressBar.invisible()
                }
            })

        viewModelPrev.loadData(idLeague)
        viewModelPrev.observePrevMatch().observe(this,
            Observer {
                events.clear()
                events.addAll(it.events)
                adapter.notifyDataSetChanged()

            })

        swipeRefreshLayout.onRefresh {
            viewModelPrev.loadData(idLeague)
            viewModelPrev.observePrevMatch().observe(this,
                Observer {
                    events.clear()
                    events.addAll(it.events)
                    adapter.notifyDataSetChanged()
                    swipeRefreshLayout.isRefreshing = false
                })
        }

        return rootView
    }


    override fun onPrepareOptionsMenu(menu: Menu?) {
        val menuItem = menu?.findItem(R.id.searchMatch)
        val search = menuItem?.actionView as SearchView
        searching(search)

        super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        activity?.menuInflater?.inflate(R.menu.navigation, menu)

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
                getSearch(query)

                return true
            }
        })
    }

    private val apiRepositori = ApiRepositori()
    private val gson = Gson()
    fun getSearch(query: String?) {
        doAsync {
            progressBar.visible()
            try {
                val data = gson.fromJson(
                    apiRepositori.doRequest(TheSportDBApi.getSearch(query)),
                    EventsResponse::class.java
                )

                uiThread {
                    progressBar.invisible()
                    events.clear()
                    events.addAll(data.events)
                    adapter.notifyDataSetChanged()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
