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
import rpl1pnp.fikri.footballmatchschedule.model.DetailLeague
import rpl1pnp.fikri.footballmatchschedule.network.ApiRepositori
import rpl1pnp.fikri.footballmatchschedule.presenter.DetailLeaguePresenter
import rpl1pnp.fikri.footballmatchschedule.util.invisible
import rpl1pnp.fikri.footballmatchschedule.util.visible
import rpl1pnp.fikri.footballmatchschedule.view.DetailLeagueView

class DetailLeagueActivity : AppCompatActivity(), DetailLeagueView {
    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showDetailLeague(data: List<DetailLeague>) {
        detailLeague.clear()
        detailLeague.addAll(data)
    }

    private lateinit var presenter: DetailLeaguePresenter
    private lateinit var progressBar: ProgressBar
    private lateinit var imageView: ImageView
    private lateinit var textLeague: TextView
    private lateinit var textDesc: TextView
    private var detailLeague: MutableList<DetailLeague> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_match)

        progressBar = findViewById(R.id.progressBarDetailLeague)
        imageView = findViewById(R.id.image_logo_league)
        textLeague = findViewById(R.id.text_league)
        textDesc = findViewById(R.id.text_desc_league)

        val request = ApiRepositori()
        val gson = Gson()
        presenter = DetailLeaguePresenter(this, request, gson)

        val idLeague: String = intent.getStringExtra("idLeague")
        Log.v("Detail", idLeague)
        presenter.getLeagueDetail(idLeague)


    }

    fun bindItem(items: DetailLeague) {
        items.leagueBadge?.let { Picasso.get().load(it).fit().into(imageView) }
        textLeague.text = items.leagueName
        textDesc.text = items.leagueDescription
    }
}
