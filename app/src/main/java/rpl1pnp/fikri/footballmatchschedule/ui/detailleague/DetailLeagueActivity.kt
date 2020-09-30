package rpl1pnp.fikri.footballmatchschedule.ui.detailleague

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_league.*
import rpl1pnp.fikri.footballmatchschedule.R
import rpl1pnp.fikri.footballmatchschedule.model.LeagueDetail
import rpl1pnp.fikri.footballmatchschedule.util.invisible
import rpl1pnp.fikri.footballmatchschedule.util.visible

class DetailLeagueActivity : AppCompatActivity() {
    private lateinit var viewModelDetailLeague: DetailLeagueViewModel
    private var leagues: MutableList<LeagueDetail> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_league)
        viewModelDetailLeague = ViewModelProvider(this).get(DetailLeagueViewModel::class.java)
        val idLeague: String? = intent.getStringExtra("idLeague")
        viewModelDetailLeague.getLeagueList(idLeague)
        viewModelDetailLeague.observeLeague().observe(this,
            {
                leagues.clear()
                leagues.addAll(it.leagues)
                showLeagueList(leagues)
                Log.v("next2", idLeague + "")
            })
        viewModelDetailLeague.observeLoading().observe(this,
            {
                progressBar(it)
            })
    }

    fun showLeagueList(data: List<LeagueDetail>) {
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
