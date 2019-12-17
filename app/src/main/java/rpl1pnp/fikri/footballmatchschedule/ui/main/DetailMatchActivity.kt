package rpl1pnp.fikri.footballmatchschedule.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import rpl1pnp.fikri.footballmatchschedule.R

class DetailMatchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_match)
        val eventId = intent.getStringExtra("EVENT_ID")
        val homeTeamId = intent.getStringExtra("HOME_TEAM")
        val awayTeamId = intent.getStringExtra("AWAY_TEEAM")


    }
}
