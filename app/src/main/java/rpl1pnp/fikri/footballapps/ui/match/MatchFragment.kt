package rpl1pnp.fikri.footballapps.ui.match

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_match.*
import org.jetbrains.anko.singleTop
import org.jetbrains.anko.support.v4.intentFor
import rpl1pnp.fikri.footballapps.R
import rpl1pnp.fikri.footballapps.adapter.EventsAdapter
import rpl1pnp.fikri.footballapps.model.Events
import rpl1pnp.fikri.footballapps.ui.detailmatch.DetailMatchActivity
import rpl1pnp.fikri.footballapps.view.MatchView

class MatchFragment : Fragment(), MatchView {
    private lateinit var presenter: MatchPresenter
    private lateinit var adapterNext: EventsAdapter
    private lateinit var adapterLast: EventsAdapter
    private var eventsNext: MutableList<Events> = mutableListOf()
    private var eventsLast: MutableList<Events> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View = inflater.inflate(R.layout.fragment_match, container, false)
        rv_next_match.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        rv_last_match.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
    }

    override fun showLoading() {
        TODO("Not yet implemented")
    }

    override fun hideLoading() {
        TODO("Not yet implemented")
    }

    override fun showListNextMatch(data: List<Events>) {
        TODO("Not yet implemented")
    }

    override fun showListLastMatch(data: List<Events>) {
        TODO("Not yet implemented")
    }

    override fun searchMatch(data: List<Events>) {
        TODO("Not yet implemented")
    }

    override fun nullData() {
        TODO("Not yet implemented")
    }

}