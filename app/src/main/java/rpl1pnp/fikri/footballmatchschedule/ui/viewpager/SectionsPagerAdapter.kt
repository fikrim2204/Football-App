package rpl1pnp.fikri.footballmatchschedule.ui.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import rpl1pnp.fikri.footballmatchschedule.ui.match.nextmatch.NextNextMatchFragment
import rpl1pnp.fikri.footballmatchschedule.ui.match.prevmatch.PreviousMatchFragment
import rpl1pnp.fikri.footballmatchschedule.ui.viewpager.PlaceholderFragment.Companion.NEXT_MATCH
import rpl1pnp.fikri.footballmatchschedule.ui.viewpager.PlaceholderFragment.Companion.PREV_MATCH

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    private val pages = listOf(
        PreviousMatchFragment(),
        NextNextMatchFragment()
    )

    override fun getItem(position: Int): Fragment {
        var fragment = Fragment()
        if (position == 0) {
            fragment = PreviousMatchFragment()
        } else if (position == 1) {
            fragment = NextNextMatchFragment()
        }
        return fragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> PREV_MATCH
            else -> NEXT_MATCH
        }
    }

    override fun getCount(): Int {
        return pages.size
    }
}