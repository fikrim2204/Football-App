package rpl1pnp.fikri.footballapps.ui.detailleague

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_league.*
import rpl1pnp.fikri.footballapps.R
import rpl1pnp.fikri.footballapps.model.LeagueDetail
import rpl1pnp.fikri.footballapps.network.ApiRepository
import rpl1pnp.fikri.footballapps.util.invisible
import rpl1pnp.fikri.footballapps.util.visible
import rpl1pnp.fikri.footballapps.view.DetailLeagueView

class DetailLeagueActivity : AppCompatActivity(), DetailLeagueView {
    private lateinit var presenter: DetailLeaguePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_league)
        setSupportActionBar(toolbar_league_detail)
        supportActionBar?.title = getString(R.string.league_detail)
        val idLeague: String? = intent.getStringExtra("idLeague")

        val request = ApiRepository()
        val gson = Gson()
        presenter = DetailLeaguePresenter(this, request, gson)
        presenter.getLeagueList(idLeague)
    }

    override fun showLoading() {
        progressBar(true)
    }

    override fun hideLoading() {
        progressBar(false)
    }

    override fun showLeagueList(data: List<LeagueDetail>) {
        Picasso.get().load(data.first().leagueBadge.orEmpty()).fit().into(image_logo_league)
        text_name_league.text = data.first().leagueName.orEmpty()
        text_desc_league.text = data.first().leagueDescription.orEmpty()
    }

    private fun progressBar(isTrue: Boolean) {
        if (isTrue) {
            return progressbar_detail_league.visible()
        }
        return progressbar_detail_league.invisible()
    }
}
