package rpl1pnp.fikri.footballapps.view

import rpl1pnp.fikri.footballapps.model.Table

interface StandingView {
    fun showLoading()
    fun hideLoading()
    fun getTable(data: List<Table>)
}