package rpl1pnp.fikri.footballmatchschedule.ui.detailleague

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_league.*
import rpl1pnp.fikri.footballmatchschedule.R
import rpl1pnp.fikri.footballmatchschedule.model.LeagueDetail
import rpl1pnp.fikri.footballmatchschedule.network.ApiRepository
import rpl1pnp.fikri.footballmatchschedule.util.invisible
import rpl1pnp.fikri.footballmatchschedule.util.visible
import rpl1pnp.fikri.footballmatchschedule.view.DetailLeagueView

class DetailLeagueActivity : AppCompatActivity(), DetailLeagueView {
    private lateinit var presenter: DetailLeaguePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_league)
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
