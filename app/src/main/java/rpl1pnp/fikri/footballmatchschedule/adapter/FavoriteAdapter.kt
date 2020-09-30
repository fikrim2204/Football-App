package rpl1pnp.fikri.footballmatchschedule.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_match.view.*
import rpl1pnp.fikri.footballmatchschedule.R
import rpl1pnp.fikri.footballmatchschedule.database.Favorite

class FavoriteAdapter(
    private var favorite: List<Favorite>,
    private var listener: (Favorite) -> Unit
) : RecyclerView.Adapter<FavoriteAdapter.FavoritePreviousViewHolder>() {
    class FavoritePreviousViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindItem(favorite: Favorite, listener: (Favorite) -> Unit) {
            itemView.team_home.text = favorite.homeTeam
            itemView.team_away.text = favorite.awayTeam
            itemView.score_home.text = favorite.homeScore
            itemView.score_away.text = favorite.awayScore
            if (favorite.homeScore == null && favorite.awayScore == null) {
                itemView.strip.text = itemView.resources.getString(R.string.vs)
            } else {
                itemView.strip.text = itemView.resources.getString(R.string.strip)
            }
            itemView.date.text = favorite.dateEvent
            itemView.setOnClickListener {
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritePreviousViewHolder {
        return FavoritePreviousViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_match, parent, false)
        )
    }

    override fun onBindViewHolder(holder: FavoritePreviousViewHolder, position: Int) {
        return holder.bindItem(favorite[position], listener)
    }

    override fun getItemCount(): Int {
        return favorite.size
    }
}