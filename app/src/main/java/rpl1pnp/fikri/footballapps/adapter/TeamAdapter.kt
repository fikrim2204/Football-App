package rpl1pnp.fikri.footballapps.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_team.view.*
import rpl1pnp.fikri.footballapps.R
import rpl1pnp.fikri.footballapps.model.Team

class TeamAdapter(private var teams: List<Team>, private val listener: (Team) -> Unit) :
    RecyclerView.Adapter<TeamAdapter.TeamViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        return TeamViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_team, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        return holder.bindItem(teams[position], listener)
    }

    override fun getItemCount() = teams.size

    class TeamViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindItem(team: Team, listener: (Team) -> Unit) {
            team.strTeamBadge.let { Picasso.get().load(it).into(itemView.iv_search_team) }
            itemView.tv_search_team.text = team.strTeam
        }

    }
}