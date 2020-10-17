package rpl1pnp.fikri.footballapps.ui.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import rpl1pnp.fikri.footballapps.ui.match.MatchFragment
import rpl1pnp.fikri.footballapps.ui.standings.StandingsFragment
import rpl1pnp.fikri.footballapps.ui.team.TeamFragment
import rpl1pnp.fikri.footballapps.ui.viewpager.PlaceholderFragment.Companion.MATCH
import rpl1pnp.fikri.footballapps.ui.viewpager.PlaceholderFragment.Companion.STANDINGS
import rpl1pnp.fikri.footballapps.ui.viewpager.PlaceholderFragment.Companion.TEAM

class SectionsPagerAdapter(fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val pages = listOf(
        MatchFragment(),
        StandingsFragment(),
        TeamFragment()
    )

    override fun getItem(position: Int): Fragment {
        var fragment = Fragment()
        if (position == 0) {
            fragment = MatchFragment()
        } else if (position == 1) {
            fragment = StandingsFragment()
        } else {
            fragment = TeamFragment()
        }
        return fragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> MATCH
            1 -> STANDINGS
            else -> TEAM
        }
    }

    override fun getCount(): Int {
        return pages.size
    }
}