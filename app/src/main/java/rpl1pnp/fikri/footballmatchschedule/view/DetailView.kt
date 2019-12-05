package rpl1pnp.fikri.footballmatchschedule.view

import rpl1pnp.fikri.footballmatchschedule.model.Events

interface DetailView {
    fun showLoading()
    fun hideLoading()
    fun showNextMatch(data: List<Events>)
    fun showPreviousMatch(data: List<Events>)
}