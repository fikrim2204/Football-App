package rpl1pnp.fikri.footballmatchschedule.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_detail_match.*
import rpl1pnp.fikri.footballmatchschedule.R
import rpl1pnp.fikri.footballmatchschedule.model.Events
import rpl1pnp.fikri.footballmatchschedule.network.ApiRepositori
import rpl1pnp.fikri.footballmatchschedule.presenter.MatchDetailPresenter
import rpl1pnp.fikri.footballmatchschedule.util.invisible
import rpl1pnp.fikri.footballmatchschedule.util.visible
import rpl1pnp.fikri.footballmatchschedule.view.DetailMatchView

class DetailMatchActivity : AppCompatActivity(), DetailMatchView {
    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    @SuppressLint("SetTextI18n")
    override fun showDetail(data: List<Events>) {
        text_event_date.text = data.first().dateEvent.orEmpty()
        text_home_name.text = data.first().homeTeam.orEmpty()
        text_home_score.text = data.first().homeScore.orEmpty()
        text_home_goals.text = data.first().homeGoalDetail.orEmpty()
        text_home_formation.text = data.first().homeFormation.orEmpty()
        text_home_red_cards.text = data.first().homeRedCard.orEmpty()
        text_home_yellow_cards.text = data.first().homeYellowCard.orEmpty()

        text_away_name.text = data.first().awayTeam.orEmpty()
        text_away_score.text = data.first().awayScore.orEmpty()
        text_away_goals.text = data.first().awayGoalDetail.orEmpty()
        text_away_formation.text = data.first().awayFormation.orEmpty()
        text_away_red_cards.text = data.first().awayRedCard.orEmpty()
        text_home_yellow_cards.text = data.first().awayYellowCard.orEmpty()

        if (data.first().homeScore === null && data.first().awayScore === null) {
            text_divide_score.text = " Vs "
        } else {
            text_divide_score.text = " - "
        }
    }

//    override fun getLogoTeam(data: List<Team>, isHomeTeam: Boolean) {
//        if (isHomeTeam) {
//            Picasso.get().load(data.first().teamBadge.orEmpty()).fit().into(image_home_badge)
//        } else {
//            Picasso.get().load(data.first().teamBadge.orEmpty()).fit().into(image_away_badge)
//        }
//    }

    private lateinit var presenter: MatchDetailPresenter
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_match)
        progressBar = findViewById(R.id.progress_detail_match)

        val eventId = intent.getStringExtra("EVENT_ID")
        val homeTeamId = intent.getStringExtra("HOME_TEAM")
        val awayTeamId = intent.getStringExtra("AWAY_TEAM")
        Log.v("Home", homeTeamId)
        Log.v("Away", awayTeamId)

        val api = ApiRepositori()
        val gson = Gson()
        presenter = MatchDetailPresenter(this, api, gson)
        presenter.getEvent(eventId)
//        presenter.getLogo(homeTeamId, true)
//        presenter.getLogo(awayTeamId, false)


    }
}
