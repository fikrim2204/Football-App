package rpl1pnp.fikri.footballmatchschedule.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import rpl1pnp.fikri.footballmatchschedule.R

class MatchActivity : AppCompatActivity(){

    private lateinit var viewModel: PageViewModel
    var idLeague: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)


        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
        val fab: FloatingActionButton = findViewById(R.id.fab)

        if (intent.extras != null) {
            val bundle = intent.extras
            idLeague = bundle?.getString("idLeague")
            viewModel = ViewModelProviders.of(this).get(PageViewModel::class.java)
            viewModel.setIdLeague(idLeague)
            Log.v("Match", idLeague + "")
        }


        fab.setOnClickListener {
            val intent = Intent(this, DetailLeagueActivity::class.java)
            intent.putExtra("idLeague", idLeague)
            startActivity(intent)
        }
    }
}