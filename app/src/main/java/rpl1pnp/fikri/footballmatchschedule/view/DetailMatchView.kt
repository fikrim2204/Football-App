package rpl1pnp.fikri.footballmatchschedule.view

import rpl1pnp.fikri.footballmatchschedule.model.Events
import rpl1pnp.fikri.footballmatchschedule.model.Team

interface DetailMatchView {
    fun showLoading()
    fun hideLoading()
    fun showDetailEvent(data: Events)
    fun getLogoTeam(data: Team, isHomeTeam: Boolean)
}