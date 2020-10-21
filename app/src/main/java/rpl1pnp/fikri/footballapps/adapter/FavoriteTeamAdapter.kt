package rpl1pnp.fikri.footballapps.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_team.view.*
import rpl1pnp.fikri.footballapps.R
import rpl1pnp.fikri.footballapps.database.FavoriteTeam

class FavoriteTeamAdapter(private var favoriteTeam: List<FavoriteTeam>) :
    RecyclerView.Adapter<FavoriteTeamAdapter.FavoriteTeamViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteTeamViewHolder {
        return FavoriteTeamViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_team, parent, false)
        )
    }

    override fun onBindViewHolder(
        holderFavoriteTeam: FavoriteTeamViewHolder,
        position: Int
    ) {
        return holderFavoriteTeam.bindItem(favoriteTeam[position])
    }

    override fun getItemCount() = favoriteTeam.size

    class FavoriteTeamViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindItem(favoriteTeam: FavoriteTeam) {
            favoriteTeam.teamBadge.let {
                Picasso.get().load(it).resize(160, 160).into(itemView.iv_team)
            }
            itemView.tv_team.text = favoriteTeam.team
        }

    }
}