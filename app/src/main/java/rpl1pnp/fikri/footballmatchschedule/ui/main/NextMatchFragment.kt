package rpl1pnp.fikri.footballmatchschedule.ui.main


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import org.jetbrains.anko.support.v4.onRefresh
import rpl1pnp.fikri.footballmatchschedule.R
import rpl1pnp.fikri.footballmatchschedule.adapter.EventAdapter
import rpl1pnp.fikri.footballmatchschedule.model.Events
import rpl1pnp.fikri.footballmatchschedule.presenter.MatchPresenter
import rpl1pnp.fikri.footballmatchschedule.util.invisible
import rpl1pnp.fikri.footballmatchschedule.util.visible

/**
 * A simple [Fragment] subclass.
 */
class NextMatchFragment : Fragment() {
    private lateinit var viewModel: PageViewModel
    private lateinit var viewModelNext: NextMatchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(requireActivity()).get(PageViewModel::class.java)
        viewModelNext = ViewModelProviders.of(this).get(NextMatchViewModel::class.java)
    }

    private var events: MutableList<Events> = mutableListOf()
    private lateinit var nextList: RecyclerView
    private lateinit var presenter: MatchPresenter
    private lateinit var adapter: EventAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private var idLeague: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView: View = inflater.inflate(R.layout.fragment_next_match, container, false)
        nextList = rootView.findViewById(R.id.recycler_next_match) as RecyclerView
        nextList.layoutManager = LinearLayoutManager(activity)

        adapter = EventAdapter(events) {
        }

        nextList.adapter = adapter
        progressBar = rootView.findViewById(R.id.progressBarNext) as ProgressBar
        swipeRefreshLayout =
            rootView.findViewById(R.id.swipeRefreshLayoutNext) as SwipeRefreshLayout

        viewModelNext.isLoading().observe(this,
            Observer {
                if (it == true) {
                    progressBar.visible()
                } else {
                    progressBar.invisible()
                }
            })

        idLeague = viewModel.getIdLeague()
        viewModelNext.loadData(idLeague)
        viewModelNext.observeNextMatch().observe(this,
            Observer {
                events.clear()
                events.addAll(it.events)
                adapter.notifyDataSetChanged()
                Log.v("next2", idLeague + "")
            })


        swipeRefreshLayout.onRefresh {
            viewModelNext.loadData(idLeague)
        }

        return rootView
    }


}
