package rpl1pnp.fikri.footballapps.view

import rpl1pnp.fikri.footballapps.model.Event

interface NextMatchView {
    //Delete Soon
    fun showLoading()
    fun hideLoading()
    fun showListMatch(data: List<Event>)
    fun searchMatch(data: List<Event>)
    fun nullData()
}