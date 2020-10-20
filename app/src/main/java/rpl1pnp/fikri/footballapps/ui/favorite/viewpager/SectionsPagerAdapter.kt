package rpl1pnp.fikri.footballapps.ui.favorite.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import rpl1pnp.fikri.footballapps.ui.favorite.FavoriteLastFragment
import rpl1pnp.fikri.footballapps.ui.favorite.FavoriteNextFragment
import rpl1pnp.fikri.footballapps.ui.favorite.viewpager.PlaceholderFragment.Companion.NEXT_MATCH
import rpl1pnp.fikri.footballapps.ui.favorite.viewpager.PlaceholderFragment.Companion.PREV_MATCH


class SectionsPagerAdapter(fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    private val pages = listOf(
        FavoriteLastFragment(),
        FavoriteNextFragment()
    )

    override fun getItem(position: Int): Fragment {
        var fragment = Fragment()
        if (position == 0) {
            fragment = FavoriteLastFragment()
        } else if (position == 1) {
            fragment = FavoriteNextFragment()
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