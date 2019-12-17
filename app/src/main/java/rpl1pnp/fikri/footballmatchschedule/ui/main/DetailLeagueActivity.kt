package rpl1pnp.fikri.footballmatchschedule.ui.main

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import rpl1pnp.fikri.footballmatchschedule.R
import rpl1pnp.fikri.footballmatchschedule.model.LeagueDetail
import rpl1pnp.fikri.footballmatchschedule.network.ApiRepositori
import rpl1pnp.fikri.footballmatchschedule.presenter.MatchPresenter
import rpl1pnp.fikri.footballmatchschedule.util.invisible
import rpl1pnp.fikri.footballmatchschedule.util.visible
import rpl1pnp.fikri.footballmatchschedule.view.MatchView

class DetailLeagueActivity : AppCompatActivity(), MatchView{
    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showLeagueList(data: LeagueDetail) {
        data.leagueBadge?.let { Picasso.get().load(it).fit().into(imageView) }
        textLeague.text = data.leagueName
        desc = data.leagueDescription.toString()
        textDesc.text = desc
        Log.v("detailleague", desc)
    }

    private lateinit var progressBar: ProgressBar
    private lateinit var imageView: ImageView
    private lateinit var textLeague: TextView
    private lateinit var textDesc: TextView
    private lateinit var presenter: MatchPresenter
    private var desc: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_league)

        progressBar = findViewById(R.id.progressbar_detail_league)
        imageView = findViewById(R.id.image_logo_league)
        textLeague = findViewById(R.id.text_name_league)
        textDesc = findViewById(R.id.text_desc_league)

        val request = ApiRepositori()
        val gson = Gson()
        presenter = MatchPresenter(this, request, gson)

        val idLeague: String? = intent.getStringExtra("idLeague")
        presenter.getLeagueList(idLeague)

    }
}
