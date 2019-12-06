package rpl1pnp.fikri.footballmatchschedule.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    private val pages = listOf(
        PreviousMatchFragment(),
        NextMatchFragment()
    )

    override fun getItem(position: Int): Fragment? {
        var fragment: Fragment? = null
        if (position == 0) {
            fragment = PreviousMatchFragment()
        } else if (position == 1) {
            fragment = NextMatchFragment()
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