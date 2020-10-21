package rpl1pnp.fikri.footballapps.ui.detailteam

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_team.*
import kotlinx.android.synthetic.main.item_detail_team.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
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

    private val coroutineContextProvider = CoroutineContextProvider()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_team)

        job = Job()
        val idTeam = intent.getStringExtra("ID_TEAM")

        val request = ApiRepository()
        val gson = Gson()
        presenter = DetailTeamPresenter(this, request, gson)
        launch { presenter.getDetailTeam(idTeam) }
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

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}