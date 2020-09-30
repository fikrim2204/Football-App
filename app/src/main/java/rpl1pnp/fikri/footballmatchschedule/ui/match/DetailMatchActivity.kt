package rpl1pnp.fikri.footballmatchschedule.ui.match

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_match.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar
import rpl1pnp.fikri.footballmatchschedule.R
import rpl1pnp.fikri.footballmatchschedule.database.Favorite
import rpl1pnp.fikri.footballmatchschedule.database.database
import rpl1pnp.fikri.footballmatchschedule.model.Events
import rpl1pnp.fikri.footballmatchschedule.model.Team
import rpl1pnp.fikri.footballmatchschedule.util.invisible
import rpl1pnp.fikri.footballmatchschedule.util.visible
import java.text.SimpleDateFormat
import java.util.*

class DetailMatchActivity : AppCompatActivity() {
    private lateinit var viewModelMatch: MatchViewModel
    private lateinit var progressBar: ProgressBar
    private var events: MutableList<Events> = mutableListOf()
    private var teamHome: MutableList<Team> = mutableListOf()
    private var teamAway: MutableList<Team> = mutableListOf()
    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false
    private var eventId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_match)
        progressBar = findViewById(R.id.progress_detail_match)
        Log.d("FAVdefault", "$isFavorite")

        eventId = intent.getStringExtra("EVENT_ID")
        val homeTeamId = intent.getStringExtra("HOME_TEAM")
        val awayTeamId = intent.getStringExtra("AWAY_TEAM")
        viewModelMatch = ViewModelProvider(this).get(MatchViewModel::class.java)
        viewModelMatch.getEvent(eventId)
        viewModelMatch.getLogoHome(homeTeamId)
        viewModelMatch.getLogoAway(awayTeamId)

        favoriteState()
        viewModelMatch.observeEvent().observe(this,
            {
                events.clear()
                events.addAll(it.events)
                showDetail(events)
                Log.v("event", eventId + "")
            })
        viewModelMatch.observeLogoHome().observe(this,
            {
                teamHome.clear()
                teamHome.addAll(it.teams)
                getLogoTeam(teamHome, true)
                Log.v("nextHome", awayTeamId + "")
            })
        viewModelMatch.observeLogoAway().observe(this,
            {
                teamAway.clear()
                teamAway.addAll(it.teams)
                getLogoTeam(teamAway, false)
                Log.v("nextAway", awayTeamId + "")
            })
        viewModelMatch.observeLoading().observe(this,
            {
                progressBar(it)
            })
    }

    fun getLogoTeam(data: List<Team>, isHomeTeam: Boolean) {
        if (isHomeTeam) {
            Picasso.get().load(data.first().teamBadge.orEmpty()).fit().into(image_home_badge)
        } else {
            Picasso.get().load(data.first().teamBadge.orEmpty()).fit().into(image_away_badge)
        }
    }

    fun showDetail(data: List<Events>) {
        val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        df.timeZone = TimeZone.getTimeZone("UTC")
        val date: Date? = df.parse(data.first().dateEvent + " " + data.first().time)
        df.timeZone = (TimeZone.getDefault())
        val formattedDate: String? = df.format(date)
        text_event_date.text = formattedDate.orEmpty()
        text_home_name.text = data.first().homeTeam.orEmpty()
        text_home_score.text = data.first().homeScore.orEmpty()
        text_home_goals.text = data.first().homeGoalDetail.orEmpty()
        text_home_formation.text = data.first().homeFormation.orEmpty()
        text_home_red_cards.text = data.first().homeRedCard.orEmpty()
        text_home_yellow_cards.text = data.first().homeYellowCard.orEmpty()
        text_home_formation.text = data.first().homeFormation.orEmpty()

        text_away_name.text = data.first().awayTeam.orEmpty()
        text_away_score.text = data.first().awayScore.orEmpty()
        text_away_goals.text = data.first().awayGoalDetail.orEmpty()
        text_away_formation.text = data.first().awayFormation.orEmpty()
        text_away_red_cards.text = data.first().awayRedCard.orEmpty()
        text_home_yellow_cards.text = data.first().awayYellowCard.orEmpty()
        text_away_formation.text = data.first().awayFormation.orEmpty()

        if (data.first().homeScore == null && data.first().awayScore == null) {
            text_divide_score.text = resources.getString(R.string.vs)
        } else {
            text_divide_score.text = resources.getString(R.string.strip)
        }
    }

    fun progressBar(isTrue: Boolean) {
        if (isTrue) {
            return progress_detail_match.visible()
        }
        return progress_detail_match.invisible()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.favorite -> {
                if (isFavorite) removeFromFavorite() else addToFavorite()

                isFavorite = !isFavorite
                setFavorite()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun addToFavorite() {
        try {
            database.use {
                insert(
                    Favorite.TABLE_FAVORITE,
                    Favorite.ID_EVENT to events.first().eventId,
                    Favorite.ID_LEAGUE to events.first().idLeague,
                    Favorite.ID_HOME_TEAM to events.first().homeTeamId,
                    Favorite.ID_AWAY_TEAM to events.first().awayTeamId,
                    Favorite.HOME_TEAM to events.first().homeTeam,
                    Favorite.AWAY_TEAM to events.first().awayTeam,
                    Favorite.HOME_SCORE to events.first().homeScore,
                    Favorite.AWAY_SCORE to events.first().awayScore,
                    Favorite.DATE_EVENT to events.first().dateEvent,
                    Favorite.TIME to events.first().time
                )
            }
            ly_detail_match.snackbar("Added to Favorit")
        } catch (e: SQLiteConstraintException) {
            ly_detail_match.snackbar("Removed from Favorit")
        }
    }

    private fun removeFromFavorite() {
        Log.i("TAG", eventId)
        try {
            database.use {
                delete(
                    Favorite.TABLE_FAVORITE, "(ID_EVENT = {id})",
                    "id" to eventId.toString()
                )
            }
            ly_detail_match.snackbar("Removed from Favorit")
        } catch (e: SQLiteConstraintException) {
            ly_detail_match.snackbar(e.localizedMessage)
        }
    }

    private fun setFavorite() {
        if (isFavorite) {
            Log.d("FAVset", "$isFavorite")
            menuItem?.getItem(0)?.icon =
                ContextCompat.getDrawable(this, R.drawable.ic_added_favorite)
        } else {
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_add_favorite)
        }
    }

    private fun favoriteState() {
        database.use {
            val result = select(Favorite.TABLE_FAVORITE)
                .whereArgs(
                    "(ID_EVENT = {id})",
                    "id" to eventId.toString()
                )
            val favorite = result.parseList(classParser<Favorite>())
            if (favorite.isNotEmpty()) isFavorite = true
            Log.d("FAVstate", "$isFavorite")
        }
    }
}