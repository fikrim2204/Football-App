package rpl1pnp.fikri.footballapps.view

import rpl1pnp.fikri.footballapps.model.Event

interface MatchView {
    fun showLoading()
    fun hideLoading()
    fun showListNextMatch(data: List<Event>)
    fun showListLastMatch(data: List<Event>)
    fun isNullData(state: Boolean)
}