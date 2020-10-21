package rpl1pnp.fikri.footballapps.ui.match

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_match.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.jetbrains.anko.singleTop
import org.jetbrains.anko.support.v4.intentFor
import rpl1pnp.fikri.footballapps.R
import rpl1pnp.fikri.footballapps.adapter.EventsAdapter
import rpl1pnp.fikri.footballapps.model.Event
import rpl1pnp.fikri.footballapps.network.ApiRepository
import rpl1pnp.fikri.footballapps.ui.detailmatch.DetailMatchActivity
import rpl1pnp.fikri.footballapps.ui.league.viewpager.PageViewModel
import rpl1pnp.fikri.footballapps.util.CoroutineContextProvider
import rpl1pnp.fikri.footballapps.util.invisible
import rpl1pnp.fikri.footballapps.util.visible
import rpl1pnp.fikri.footballapps.view.MatchView
import kotlin.coroutines.CoroutineContext

class MatchFragment : Fragment(), MatchView, CoroutineScope {
    private lateinit var viewModel: PageViewModel
    private lateinit var presenter: MatchPresenter
    private lateinit var adapterNext: EventsAdapter
    private lateinit var adapterLast: EventsAdapter
    private var eventNext: MutableList<Event> = mutableListOf()
    private var eventLast: MutableList<Event> = mutableListOf()
    private val coroutineContextProvider = CoroutineContextProvider()
    private lateinit var job: Job
    var idLeague: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        viewModel = ViewModelProvider(requireActivity()).get(PageViewModel::class.java)
        job = Job()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_match, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView()

        idLeague = viewModel.getIdLeague()
        getDataPresenter()
    }

    private fun getDataPresenter() {
        val request = ApiRepository()
        val gson = Gson()
        presenter = MatchPresenter(this, request, gson)

        launch {
            presenter.getListLastMatch(idLeague)
            presenter.getListNextMatch(idLeague)
        }
    }

    override fun showLoading() {
        pb_match_league?.visible()
    }

    override fun hideLoading() {
        pb_match_league?.invisible()
    }

    override fun showListNextMatch(data: List<Event>) {
        eventNext.clear()
        eventNext.addAll(data)
        adapterNext.notifyDataSetChanged()
    }

    override fun showListLastMatch(data: List<Event>) {
        eventLast.clear()
        eventLast.addAll(data)
        adapterLast.notifyDataSetChanged()
    }

    override fun isNullData(state: Boolean) {
        if (state) {
            null_data_match.visible()
        } else {
            null_data_match.invisible()
        }
    }

    private fun recyclerView() {
        rv_next_match?.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)

        rv_last_match?.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)

        adapterLast = EventsAdapter(eventLast) { last ->
            val idEventLast = last.eventId.toString()
            val idHomeLast = last.homeTeamId.toString()
            val idAwayLast = last.awayTeamId.toString()
            startActivity(
                intentFor<DetailMatchActivity>(
                    "EVENT_ID" to idEventLast,
                    "HOME_TEAM" to idHomeLast,
                    "AWAY_TEAM" to idAwayLast
                ).singleTop()
            )
        }

        adapterNext = EventsAdapter(eventNext) { next ->
            val idEventNext = next.eventId.toString()
            val idHomeNext = next.homeTeamId.toString()
            val idAwayNext = next.awayTeamId.toString()
            startActivity(
                intentFor<DetailMatchActivity>(
                    "EVENT_ID" to idEventNext,
                    "HOME_TEAM" to idHomeNext,
                    "AWAY_TEAM" to idAwayNext
                ).singleTop()
            )
        }

        rv_last_match?.adapter = adapterLast
        rv_next_match?.adapter = adapterNext
    }

    override val coroutineContext: CoroutineContext
        get() = job + coroutineContextProvider.main

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}