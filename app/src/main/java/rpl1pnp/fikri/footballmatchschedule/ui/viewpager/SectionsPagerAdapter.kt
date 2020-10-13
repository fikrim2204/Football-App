package rpl1pnp.fikri.footballmatchschedule.ui.viewpager

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import rpl1pnp.fikri.footballmatchschedule.ui.match.nextmatch.NextNextMatchFragment
import rpl1pnp.fikri.footballmatchschedule.ui.match.prevmatch.PreviousMatchFragment

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
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
            0 -> "Previous Match"
            else -> "Next Match"
        }
    }

    override fun getCount(): Int {
        return pages.size
    }
}