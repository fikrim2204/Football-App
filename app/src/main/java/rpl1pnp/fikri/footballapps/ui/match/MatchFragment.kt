package rpl1pnp.fikri.footballapps.ui.match

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_match.*
import org.jetbrains.anko.singleTop
import org.jetbrains.anko.support.v4.intentFor
import rpl1pnp.fikri.footballapps.R
import rpl1pnp.fikri.footballapps.adapter.EventsAdapter
import rpl1pnp.fikri.footballapps.model.Events
import rpl1pnp.fikri.footballapps.ui.detailmatch.DetailMatchActivity

class MatchFragment : Fragment() {
    private lateinit var viewModel: MatchViewModel
    private lateinit var adapterNext: EventsAdapter
    private lateinit var adapterLast: EventsAdapter
    private var eventsNext: MutableList<Events> = mutableListOf()
    private var eventsLast: MutableList<Events> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        viewModel = ViewModelProvider(requireActivity()).get(MatchViewModel::class.java)
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

        return rootView
    }

}