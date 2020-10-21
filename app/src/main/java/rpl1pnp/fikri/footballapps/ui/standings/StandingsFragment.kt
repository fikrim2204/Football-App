package rpl1pnp.fikri.footballapps.ui.standings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_standings.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import rpl1pnp.fikri.footballapps.R
import rpl1pnp.fikri.footballapps.adapter.StandingAdapter
import rpl1pnp.fikri.footballapps.model.Table
import rpl1pnp.fikri.footballapps.network.ApiRepository
import rpl1pnp.fikri.footballapps.ui.league.viewpager.PageViewModel
import rpl1pnp.fikri.footballapps.util.CoroutineContextProvider
import rpl1pnp.fikri.footballapps.util.invisible
import rpl1pnp.fikri.footballapps.util.visible
import rpl1pnp.fikri.footballapps.view.StandingView
import kotlin.coroutines.CoroutineContext


class StandingsFragment : Fragment(), StandingView, CoroutineScope {
    private lateinit var viewModel: PageViewModel
    private lateinit var adapter: StandingAdapter
    private var table: MutableList<Table> = mutableListOf()
    private lateinit var presenter: StandingsPresenter
    private val coroutineContextProvider = CoroutineContextProvider()
    private lateinit var job: Job
    private var idLeague: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(PageViewModel::class.java)
        job = Job()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_standings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView()
        getDataFromPresenter()
    }

    private fun getDataFromPresenter() {
        idLeague = viewModel.getIdLeague()
        val request = ApiRepository()
        val gson = Gson()
        presenter = StandingsPresenter(this, request, gson)
        launch {
            presenter.getTable(idLeague)
        }
    }

    private fun recyclerView() {
        rv_standing.layoutManager = LinearLayoutManager(requireActivity())
        adapter = StandingAdapter(table)
        rv_standing?.adapter = adapter
    }

    override fun showLoading() {
        pb_table?.visible()
    }

    override fun hideLoading() {
        pb_table?.invisible()
    }

    override fun getTable(data: List<Table>) {
        table.clear()
        table.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override val coroutineContext: CoroutineContext
        get() = job + coroutineContextProvider.main

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }


}