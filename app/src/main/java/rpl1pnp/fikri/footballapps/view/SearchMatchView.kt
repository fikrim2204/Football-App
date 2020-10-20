package rpl1pnp.fikri.footballapps.view

import rpl1pnp.fikri.footballapps.model.Event

interface SearchMatchView {
    fun showLoading()
    fun hideLoading()
    fun searchMatch(data: List<Event>)
    fun nullData()
}
