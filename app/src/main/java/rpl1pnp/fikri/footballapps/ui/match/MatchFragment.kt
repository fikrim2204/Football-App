package rpl1pnp.fikri.footballapps.ui.match

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_match.*
import org.jetbrains.anko.singleTop
import org.jetbrains.anko.support.v4.intentFor
import rpl1pnp.fikri.footballapps.R
import rpl1pnp.fikri.footballapps.adapter.EventsAdapter
import rpl1pnp.fikri.footballapps.model.Events
import rpl1pnp.fikri.footballapps.network.ApiRepository
import rpl1pnp.fikri.footballapps.ui.detailmatch.DetailMatchActivity
import rpl1pnp.fikri.footballapps.ui.league.viewpager.PageViewModel
import rpl1pnp.fikri.footballapps.util.invisible
import rpl1pnp.fikri.footballapps.util.visible
import rpl1pnp.fikri.footballapps.view.MatchView

class MatchFragment : Fragment(), MatchView {
    private lateinit var viewModel: PageViewModel
    private lateinit var presenter: MatchPresenter
    private lateinit var adapterNext: EventsAdapter
    private lateinit var adapterLast: EventsAdapter
    private lateinit var lastList: RecyclerView
    private lateinit var nextList: RecyclerView
    private var eventsNext: MutableList<Events> = mutableListOf()
    private var eventsLast: MutableList<Events> = mutableListOf()
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
        return inflater.inflate(R.layout.fragment_match, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView()
        rv_last_match?.adapter = adapterLast
        rv_next_match?.adapter = adapterNext

        idLeague = viewModel.getIdLeague()
        val request = ApiRepository()
        val gson = Gson()
        presenter = MatchPresenter(this, request, gson)

        presenter.getListLastMatch(idLeague)
        presenter.getListNextMatch(idLeague)
    }

    override fun showLoading() {
        pb_match_league?.visible()
    }

    override fun hideLoading() {
        pb_match_league?.invisible()
    }

    override fun showListNextMatch(data: List<Events>) {
        eventsNext.clear()
        eventsNext.addAll(data)
        adapterNext.notifyDataSetChanged()
    }

    override fun showListLastMatch(data: List<Events>) {
        eventsLast.clear()
        eventsLast.addAll(data)
        adapterLast.notifyDataSetChanged()
    }

    override fun checkisNullData(state: Boolean) {
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

        adapterLast = EventsAdapter(eventsLast) { last ->
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

        adapterNext = EventsAdapter(eventsNext) { next ->
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
    }
}