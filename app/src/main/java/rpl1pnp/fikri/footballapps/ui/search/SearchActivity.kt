package rpl1pnp.fikri.footballapps.ui.search

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_search.*
import rpl1pnp.fikri.footballapps.R
import rpl1pnp.fikri.footballapps.ui.search.tabview.SectionsPagerAdapter

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setSupportActionBar(toolbar_search)
        val sectionsPagerAdapter =
            SectionsPagerAdapter(
                supportFragmentManager
            )
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
        toolbar_search.title = "Search"
    }
}