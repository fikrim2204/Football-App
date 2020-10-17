package rpl1pnp.fikri.footballapps.view

import rpl1pnp.fikri.footballapps.model.Events

interface MatchView {
    fun showLoading()
    fun hideLoading()
    fun showListNextMatch(data: List<Events>)
    fun showListLastMatch(data: List<Events>)
    fun searchMatch(data: List<Events>)
    fun nullData()
}