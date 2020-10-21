package rpl1pnp.fikri.footballapps.ui.detailteam

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_team.*
import kotlinx.android.synthetic.main.item_detail_team.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.jetbrains.anko.design.snackbar
import rpl1pnp.fikri.footballapps.R
import rpl1pnp.fikri.footballapps.model.Team
import rpl1pnp.fikri.footballapps.network.ApiRepository
import rpl1pnp.fikri.footballapps.util.CoroutineContextProvider
import rpl1pnp.fikri.footballapps.util.invisible
import rpl1pnp.fikri.footballapps.util.visible
import rpl1pnp.fikri.footballapps.view.DetailTeamView
import kotlin.coroutines.CoroutineContext

class DetailTeamActivity : AppCompatActivity(), DetailTeamView, CoroutineScope {
    private lateinit var presenter: DetailTeamPresenter
    private lateinit var job: Job
    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false
    private var teamId: String? = null
    private var teams: List<Team>? = null
    private val coroutineContextProvider = CoroutineContextProvider()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_team)
        setSupportActionBar(toolbar_detail_team)
        supportActionBar?.title = getString(R.string.detail_team)

        job = Job()
        getDataDetailTeam()
        presenter.favoriteState(this, teamId)
    }

    private fun getDataDetailTeam() {
        teamId = intent.getStringExtra("ID_TEAM")

        val request = ApiRepository()
        val gson = Gson()
        presenter = DetailTeamPresenter(this, request, gson)
        launch { presenter.getDetailTeam(teamId) }
    }

    override val coroutineContext: CoroutineContext
        get() = job + coroutineContextProvider.main

    override fun showLoading() {
        pb_detail_team?.visible()
    }

    override fun hideLoading() {
        pb_detail_team?.invisible()
    }

    override fun showDetail(data: List<Team>) {
        teams = data
        data.first().strTeamBadge.let {
            Picasso.get().load(it).error(R.drawable.ic_broken_image_gray)
                .resize(180, 180).into(iv_detail_team_badge)
        }
        data.first().strStadiumThumb.let {
            Picasso.get().load(it).error(R.drawable.ic_broken_image_gray)
                .resize(640, 480).into(iv_detail_stadium)
        }
        tv_detail_team_name.text = data.first().strTeam
        tv_detail_alternate_name.text = data.first().strAlternate
        val year = "${getString(R.string.year_formed)} ${data.first().intFormedYear}"
        tv_detail_year.text = year
        tv_detail_description.text = data.first().strDescriptionEN
        tv_detail_location.text = data.first().strStadiumLocation
        tv_detail_description_stadium.text = data.first().strStadiumDescription
        val stadium =
            "${data.first().strStadium} (${data.first().intStadiumCapacity} ${getString(R.string.capacity)})"
        tv_detail_stadium.text = stadium

    }

    override fun addFavorite() {
        ly_detail_team.snackbar(getString(R.string.added_favorite))
            .setTextColor(ContextCompat.getColor(this, R.color.textColorSnackBar))
    }

    override fun removeFavorite() {
        ly_detail_team.snackbar(getString(R.string.remove_favorite))
            .setTextColor(ContextCompat.getColor(this, R.color.textColorSnackBar))
    }

    override fun favoriteState(state: Boolean) {
        isFavorite = state
    }

    override fun errorFavorite(message: CharSequence?) {
        message?.let { ly_detail_team.snackbar(it) }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.favorite_team_menu, menu)
        menuItem = menu
        setFavorite()
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.btn_team_favorite -> {
                if (isFavorite) presenter.removeFromFavorite(
                    this,
                    teamId
                ) else presenter.addToFavorite(this, teams)
                isFavorite = !isFavorite
                setFavorite()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setFavorite() {
        if (isFavorite) {
            menuItem?.getItem(0)?.icon =
                ContextCompat.getDrawable(this, R.drawable.ic_added_favorite)
        } else {
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_add_favorite)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}