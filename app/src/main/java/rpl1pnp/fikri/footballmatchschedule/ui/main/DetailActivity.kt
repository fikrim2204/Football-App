package rpl1pnp.fikri.footballmatchschedule.ui.main

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayout
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_detail_league.image_logo_league
import kotlinx.android.synthetic.main.activity_detail_league.text_desc_league
import kotlinx.android.synthetic.main.activity_detail_league.text_name_league
import kotlinx.android.synthetic.main.item_league.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.singleTop
import rpl1pnp.fikri.footballmatchschedule.R
import rpl1pnp.fikri.footballmatchschedule.model.LeagueDetail
import rpl1pnp.fikri.footballmatchschedule.ui.detailleague.DetailLeagueActivity
import rpl1pnp.fikri.footballmatchschedule.ui.detailleague.DetailLeagueViewModel
import rpl1pnp.fikri.footballmatchschedule.ui.viewpager.PageViewModel
import rpl1pnp.fikri.footballmatchschedule.ui.viewpager.SectionsPagerAdapter

class DetailActivity : AppCompatActivity() {
    private lateinit var viewModel: PageViewModel
    private lateinit var viewModelDetailLeague: DetailLeagueViewModel
    var idLeague: String? = null
    private var leagues: MutableList<LeagueDetail> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        setSupportActionBar(toolbar_detail)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        view_pager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(view_pager)
        if (intent.extras != null) {
            val bundle = intent.extras
            idLeague = bundle?.getString("idLeague")
            viewModel = ViewModelProvider(this).get(PageViewModel::class.java)
            viewModel.setIdLeague(idLeague)
            viewModelDetailLeague = ViewModelProvider(this).get(DetailLeagueViewModel::class.java)
        }

        viewModelDetailLeague.getLeagueList(idLeague)
        viewModelDetailLeague.observeLeague().observe(this,
            {
                if (it != null) {
                    leagues.clear()
                    leagues.addAll(it.leagues)
                    showLeagueList(leagues)
                    null_data.visibility = View.GONE
                } else {
                    image_logo_league.setImageResource(R.drawable.ic_broken_image_gray)
                    text_name_league.text = resources.getText(R.string.data_null)
                    null_data.visibility = View.VISIBLE
                }
                Log.v("next2", idLeague + "")
            })
    }

    fun showLeagueList(data: List<LeagueDetail>) {
        Picasso.get().load(data.first().leagueBadge.orEmpty()).fit().into(image_logo_league)
        text_name_league.text = data.first().leagueName.orEmpty()
        text_desc_league.text = data.first().leagueDescription.orEmpty()
        card_league.setOnClickListener {
            startActivity(intentFor<DetailLeagueActivity>("idLeague" to idLeague).singleTop())
        }
    }
}