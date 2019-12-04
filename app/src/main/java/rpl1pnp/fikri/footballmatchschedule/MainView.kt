package rpl1pnp.fikri.footballmatchschedule

interface MainView {
    fun showLoading()
    fun hideLoading()
    fun showTeamList(data: List<Team>)
}