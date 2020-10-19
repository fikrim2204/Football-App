package rpl1pnp.fikri.footballapps.view

import rpl1pnp.fikri.footballapps.model.Events

interface SearchMatchView {
    fun showLoading()
    fun hideLoading()
    fun searchMatch(data: List<Events>)
    fun nullData()
}
