package rpl1pnp.fikri.footballapps.ui.favorite.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import rpl1pnp.fikri.footballapps.ui.favorite.FavoriteMatchLastFragment
import rpl1pnp.fikri.footballapps.ui.favorite.FavoriteMatchNextFragment
import rpl1pnp.fikri.footballapps.ui.favorite.FavoriteTeamFragment
import rpl1pnp.fikri.footballapps.ui.favorite.viewpager.PlaceholderFragment.Companion.LAST_MATCH
import rpl1pnp.fikri.footballapps.ui.favorite.viewpager.PlaceholderFragment.Companion.NEXT_MATCH
import rpl1pnp.fikri.footballapps.ui.favorite.viewpager.PlaceholderFragment.Companion.TEAM


class SectionsPagerAdapter(fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val pages = listOf(
        FavoriteMatchLastFragment(),
        FavoriteMatchNextFragment(),
        FavoriteTeamFragment()
    )

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> FavoriteMatchLastFragment()
            1 -> FavoriteMatchNextFragment()
            else -> FavoriteTeamFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> LAST_MATCH
            1 -> NEXT_MATCH
            else -> TEAM
        }
    }

    override fun getCount() = pages.size
}