package rpl1pnp.fikri.footballmatchschedule.view

import rpl1pnp.fikri.footballmatchschedule.model.Events

interface NextMatchView {
    fun showLoading()
    fun hideLoading()
    fun showListMatch(data: List<Events>)
    fun searchMatch(data: List<Events>)
    fun nullData()
}