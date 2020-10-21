package rpl1pnp.fikri.footballapps.ui.league.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import rpl1pnp.fikri.footballapps.ui.league.viewpager.PlaceholderFragment.Companion.MATCH
import rpl1pnp.fikri.footballapps.ui.league.viewpager.PlaceholderFragment.Companion.STANDINGS
import rpl1pnp.fikri.footballapps.ui.league.viewpager.PlaceholderFragment.Companion.TEAM
import rpl1pnp.fikri.footballapps.ui.match.MatchFragment
import rpl1pnp.fikri.footballapps.ui.standings.StandingsFragment
import rpl1pnp.fikri.footballapps.ui.team.TeamFragment

class SectionsPagerAdapter(fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val pages = listOf(
        MatchFragment(),
        StandingsFragment(),
        TeamFragment()
    )

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> MatchFragment()
            1 -> StandingsFragment()
            else -> TeamFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> MATCH
            1 -> STANDINGS
            else -> TEAM
        }
    }

    override fun getCount() = pages.size
}