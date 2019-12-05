package rpl1pnp.fikri.footballmatchschedule.ui.main

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import rpl1pnp.fikri.footballmatchschedule.R
import rpl1pnp.fikri.footballmatchschedule.adapter.MainAdapter
import rpl1pnp.fikri.footballmatchschedule.model.League
import rpl1pnp.fikri.footballmatchschedule.presenter.MainPresenter

class MainActivity : AppCompatActivity() {

    private lateinit var listTeam: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var spinner: Spinner
    private var items: MutableList<League> = mutableListOf()
    private lateinit var presenter: MainPresenter
    private lateinit var adapter: MainAdapter
    private lateinit var leagueName: String

    class MainActivityUI : AnkoComponent<MainActivity> {
        override fun createView(ui: AnkoContext<MainActivity>): View = with(ui){
            verticalLayout {
                lparams(matchParent, matchParent)
                recyclerView {
                    id = R.id.recycler_view
                }.lparams(matchParent, wrapContent)
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainActivityUI().setContentView(this)

        val recyclerLeague = find<RecyclerView>(R.id.recycler_view)
        initLeague()
        recyclerLeague.layoutManager = LinearLayoutManager(this)
        recyclerLeague.adapter = MainAdapter(items) {
            startActivity<DetailActivity>("idLeague" to it)
        }
    }

    private fun initLeague() {
        val id = resources.getIntArray(R.array.league_id)
        val photo = resources.obtainTypedArray(R.array.league_logo)
        val name = resources.getStringArray(R.array.league_name)

        items.clear()
        for (i in name.indices) {
            items.add(
                League(
                    id[i],
                    name[i],
                    photo.getResourceId(i, 0)
                )
            )
        }
        photo.recycle()
    }
}
