package rpl1pnp.fikri.footballmatchschedule.ui.match.favorite.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import rpl1pnp.fikri.footballmatchschedule.ui.match.favorite.FavoriteNextFragment
import rpl1pnp.fikri.footballmatchschedule.ui.match.favorite.FavoritePreviousFragment


class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    private val pages = listOf(
        FavoritePreviousFragment(),
        FavoriteNextFragment()
    )

    override fun getItem(position: Int): Fragment {
        var fragment = Fragment()
        if (position == 0) {
            fragment = FavoritePreviousFragment()
        } else if (position == 1) {
            fragment = FavoriteNextFragment()
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