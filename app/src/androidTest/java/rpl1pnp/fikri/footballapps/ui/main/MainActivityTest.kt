package rpl1pnp.fikri.footballapps.ui.main

import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.contrib.ViewPagerActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import rpl1pnp.fikri.footballapps.R

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @Rule
    @JvmField
    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testRecyclerMain() {
        onView(withId(R.id.rv_main)).check(matches(isDisplayed()))

        //Intent ke LeagueActivity
        onView(withText("English Premier League")).perform(click())
    }

    @Test
    fun testSearchPrev() {
        onView(withId(R.id.rv_main)).check(matches(isDisplayed()))

        //Intent ke LeagueActivity
        onView(withText("English Premier League")).perform(click())
        onView(withId(R.id.btn_navigation_search)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_navigation_search)).perform(click())
        onView(isAssignableFrom(EditText::class.java)).perform(
            typeText("chelsea"),
            pressImeActionButton()
        )
        //Memeriksa hasil pencarian
        onView(withId(R.id.rv_previous_match)).check(matches(isDisplayed()))
    }

    @Test
    fun testAddFavorite() {
        onView(withId(R.id.rv_main)).check(matches(isDisplayed()))

        //Intent ke LeagueActivity
        onView(withText("English Premier League")).perform(click())
        onView(withId(R.id.rv_previous_match)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )

        onView(withId(R.id.btn_match_favorite)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_match_favorite)).perform(click())

        pressBack()
    }

    @Test
    fun testSearchNext() {
        onView(withId(R.id.rv_main)).check(matches(isDisplayed()))

        //Intent ke LeagueActivity
        onView(withText("English Premier League")).perform(click())
        onView(withId(R.id.view_pager)).perform(ViewPagerActions.scrollRight())
        onView(withId(R.id.btn_navigation_search)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_navigation_search)).perform(click())
        onView(isAssignableFrom(EditText::class.java)).perform(
            typeText("chelsea"),
            pressImeActionButton()
        )
        //Memeriksa hasil pencarian
        onView(withId(R.id.rv_next_match)).check(matches(isDisplayed()))
    }

    @Test
    fun testNullData() {
        onView(withId(R.id.rv_main)).check(matches(isDisplayed()))

        //Intent ke LeagueActivity
        onView(withText("English Premier League")).perform(click())
        onView(withId(R.id.btn_navigation_search)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_navigation_search)).perform(click())
        onView(isAssignableFrom(EditText::class.java)).perform(
            typeText("abcdefg"),
            pressImeActionButton()
        )
        //Memeriksa hasil pencarian
        onView(withId(R.id.null_data_prev)).check(matches(isDisplayed()))
    }
}