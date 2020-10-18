package rpl1pnp.fikri.footballapps.ui.search.tabview

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import rpl1pnp.fikri.footballapps.ui.search.SearchMatchFragment
import rpl1pnp.fikri.footballapps.ui.search.SearchTeamFragment
import rpl1pnp.fikri.footballapps.ui.search.tabview.PlaceholderFragment.Companion.SEARCHMATCH
import rpl1pnp.fikri.footballapps.ui.search.tabview.PlaceholderFragment.Companion.SEARCHTEAM

class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val pages = listOf(
        SearchMatchFragment(),
        SearchTeamFragment
    )

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> SearchMatchFragment()
            else -> SearchTeamFragment()
        }

    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> SEARCHMATCH
            else -> SEARCHTEAM
        }
    }

    override fun getCount() = pages.size
}