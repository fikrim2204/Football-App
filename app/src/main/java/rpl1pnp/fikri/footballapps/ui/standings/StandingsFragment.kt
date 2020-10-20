package rpl1pnp.fikri.footballapps.ui.standings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_standings.*
import kotlinx.android.synthetic.main.layout_row.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import rpl1pnp.fikri.footballapps.R
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

        idLeague = viewModel.getIdLeague()
        val request = ApiRepository()
        val gson = Gson()
        presenter = StandingsPresenter(this, request, gson)
        launch { presenter.getTable(idLeague) }

        val tablelayoutid = activity?.findViewById(R.id.tl_standing) as TableLayout

        var index = 0
        while (index < table.size) {
            val row = layoutInflater.inflate(R.layout.layout_row, null) as TableRow
            row.no_table.text = (index + 1).toString()
            row.nama_tim.text = table[index].name
            row.goal_for.text = "${table[index].goalsfor}"
            row.goal_against.text = "${table[index].goalsagainst}"
            row.goal_difference.text = "${table[index].goalsdifference}"
            row.win.text = "${table[index].win}"
            row.draw.text = "${table[index].draw}"
            row.loss.text = "${table[index].loss}"
            row.total.text = "${table[index].total}"
            tablelayoutid.addView(row)
            index++
        }
    }

    override fun showLoading() {
        pb_table.visible()
    }

    override fun hideLoading() {
        pb_table.invisible()
    }

    override fun getTable(data: List<Table>) {
        table.clear()
        table.addAll(data)
    }

    override val coroutineContext: CoroutineContext
        get() = job + coroutineContextProvider.main

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }


}