package rpl1pnp.fikri.footballmatchschedule.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import rpl1pnp.fikri.footballmatchschedule.R
import rpl1pnp.fikri.footballmatchschedule.adapter.MainAdapter
import rpl1pnp.fikri.footballmatchschedule.model.League
import rpl1pnp.fikri.footballmatchschedule.ui.league.LeagueActivity
import rpl1pnp.fikri.footballmatchschedule.util.invisible

class MainActivity : AppCompatActivity() {

    private var idLeague: String = ""
    private lateinit var adapter: MainAdapter
    private var items: MutableList<League> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        toolbar.setTitle(R.string.app_name)
        progressbar_main.invisible()
        rv_main.layoutManager = LinearLayoutManager(this)

        initLeague()
        adapter = MainAdapter(items) {
            idLeague = it.idLeague.toString()
            val bundle = Bundle()
            bundle.putString("idLeague", idLeague)
            val intent = Intent(this, LeagueActivity::class.java)
            intent.putExtras(bundle)
            startActivity(intent)
        }
        rv_main.adapter = adapter
    }

    private fun initLeague() {
        val id = resources.getIntArray(R.array.league_id)
        val photo = resources.obtainTypedArray(R.array.league_logo)
        val name = resources.getStringArray(R.array.league_name)

        items.clear()
        for (i in name.indices) {
            items.add(
                League(
                    id[i],
                    name[i],
                    photo.getResourceId(i, 0)
                )
            )
        }
        photo.recycle()
    }

}
