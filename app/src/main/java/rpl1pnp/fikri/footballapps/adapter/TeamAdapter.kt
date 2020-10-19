package rpl1pnp.fikri.footballapps.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_team.view.*
import rpl1pnp.fikri.footballapps.R
import rpl1pnp.fikri.footballapps.model.Teams

class TeamAdapter(private var teams: List<Teams>, private val listener: (Teams) -> Unit) :
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
        fun bindItem(teams: Teams, listener: (Teams) -> Unit) {
            if (teams.strTeamBadge.isNullOrBlank()) {
                Picasso.get().load(R.drawable.ic_broken_image_gray)
                    .error(R.drawable.ic_broken_image_gray).into(itemView.iv_search_team)
            } else {
                teams.strTeamBadge.let {
                    Picasso.get().load(it).error(R.drawable.ic_broken_image_gray)
                        .into(itemView.iv_search_team)
                }
            }
            itemView.tv_search_team.text = teams.strTeam
        }

    }
}