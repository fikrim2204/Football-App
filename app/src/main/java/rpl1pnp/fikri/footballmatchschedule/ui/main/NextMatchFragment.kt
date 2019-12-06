package rpl1pnp.fikri.footballmatchschedule.ui.main


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.gson.Gson
import org.jetbrains.anko.support.v4.onRefresh
import rpl1pnp.fikri.footballmatchschedule.R
import rpl1pnp.fikri.footballmatchschedule.adapter.EventAdapter
import rpl1pnp.fikri.footballmatchschedule.model.Events
import rpl1pnp.fikri.footballmatchschedule.network.ApiRepositori
import rpl1pnp.fikri.footballmatchschedule.presenter.DetailPresenter
import rpl1pnp.fikri.footballmatchschedule.util.invisible
import rpl1pnp.fikri.footballmatchschedule.util.visible
import rpl1pnp.fikri.footballmatchschedule.view.DetailView

/**
 * A simple [Fragment] subclass.
 */
class NextMatchFragment : Fragment(), DetailView {
    private lateinit var viewModel: PageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(PageViewModel::class.java)
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showNextMatch(data: List<Events>) {
        swipeRefreshLayout.isRefreshing = false
        events.clear()
        events.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun showPreviousMatch(data: List<Events>) {
        swipeRefreshLayout.isRefreshing = false
        events.clear()
        events.addAll(data)
        adapter.notifyDataSetChanged()
    }

    private var events: MutableList<Events> = mutableListOf()
    private lateinit var nextList: RecyclerView
    private lateinit var presenter: DetailPresenter
    private lateinit var adapter: EventAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

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
        swipeRefreshLayout = rootView.findViewById(R.id.swipeRefreshLayoutNext) as SwipeRefreshLayout

        val idLeague: String? = viewModel.getSelectedItem()

        val request = ApiRepositori()
        val gson = Gson()
        presenter = DetailPresenter(this, request, gson)

        presenter.getNextMatch(idLeague)
        swipeRefreshLayout.onRefresh {
            presenter.getNextMatch(idLeague)
        }

        return rootView
    }


}
