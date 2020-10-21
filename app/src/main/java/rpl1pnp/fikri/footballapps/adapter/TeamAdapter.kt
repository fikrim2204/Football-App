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
            if (team.strTeamBadge.isBlank()) {
                Picasso.get().load(R.drawable.ic_broken_image_gray)
                    .error(R.drawable.ic_broken_image_gray).resize(100, 100)
                    .into(itemView.iv_team)
            } else {
                team.strTeamBadge.let {
                    Picasso.get().load(it).error(R.drawable.ic_broken_image_gray)
                        .into(itemView.iv_team)
                }
            }
            itemView.tv_team.text = team.strTeam
            itemView.setOnClickListener { listener(team) }
        }

    }
}