package rpl1pnp.fikri.footballapps.ui.league

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_league.image_logo_league
import kotlinx.android.synthetic.main.activity_detail_league.text_desc_league
import kotlinx.android.synthetic.main.activity_detail_league.text_name_league
import kotlinx.android.synthetic.main.activity_league.*
import kotlinx.android.synthetic.main.item_league.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.singleTop
import rpl1pnp.fikri.footballapps.R
import rpl1pnp.fikri.footballapps.model.LeagueDetail
import rpl1pnp.fikri.footballapps.network.ApiRepository
import rpl1pnp.fikri.footballapps.ui.detailleague.DetailLeagueActivity
import rpl1pnp.fikri.footballapps.ui.league.viewpager.PageViewModel
import rpl1pnp.fikri.footballapps.ui.league.viewpager.SectionsPagerAdapter
import rpl1pnp.fikri.footballapps.ui.search.SearchActivity
import rpl1pnp.fikri.footballapps.util.CoroutineContextProvider
import rpl1pnp.fikri.footballapps.view.LeagueView
import kotlin.coroutines.CoroutineContext

class LeagueActivity : AppCompatActivity(), LeagueView, CoroutineScope {
    private lateinit var viewModel: PageViewModel
    private lateinit var presenter: LeaguePresenter
    private lateinit var job: Job
    private val coroutineContextProvider = CoroutineContextProvider()
    var idLeague: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_league)
        setSupportActionBar(toolbar_detail)
        job = Job()

        setViewPager()
        getAndSetIdLeague()
        getDataPresenter()
    }

    private fun getDataPresenter() {
        val request = ApiRepository()
        val gson = Gson()
        presenter = LeaguePresenter(this, request, gson)
        launch { presenter.getLeagueList(idLeague) }
    }

    private fun setViewPager() {
        val sectionsPagerAdapter = SectionsPagerAdapter(
            supportFragmentManager
        )
        view_pager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(view_pager)
    }

    private fun getAndSetIdLeague() {
        idLeague = intent.getStringExtra("idLeague")
        viewModel = ViewModelProvider(this).get(PageViewModel::class.java)
        viewModel.setIdLeague(idLeague)
    }

    override fun showLeagueList(data: List<LeagueDetail>) {
        Picasso.get().load(data.first().leagueBadge.orEmpty()).fit()
            .error(R.drawable.ic_broken_image_gray).into(image_logo_league)
        text_name_league.text = data.first().leagueName.orEmpty()
        text_desc_league.text = data.first().leagueDescription.orEmpty()
        card_league.setOnClickListener {
            startActivity(intentFor<DetailLeagueActivity>("idLeague" to idLeague).singleTop())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_navigation, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.btn_navigation_search -> {
                startActivity(intentFor<SearchActivity>().singleTop())
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override val coroutineContext: CoroutineContext
        get() = job + coroutineContextProvider.main

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}