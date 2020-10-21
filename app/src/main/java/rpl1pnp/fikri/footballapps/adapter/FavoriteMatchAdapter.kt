package rpl1pnp.fikri.footballapps.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_match.view.*
import rpl1pnp.fikri.footballapps.R
import rpl1pnp.fikri.footballapps.database.FavoriteMatch

class FavoriteMatchAdapter(
    private var favoriteMatch: List<FavoriteMatch>
) : RecyclerView.Adapter<FavoriteMatchAdapter.FavoritePreviousViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritePreviousViewHolder {
        return FavoritePreviousViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_match, parent, false)
        )
    }

    override fun onBindViewHolder(holder: FavoritePreviousViewHolder, position: Int) {
        return holder.bindItem(favoriteMatch[position])
    }

    override fun getItemCount() = favoriteMatch.size

    class FavoritePreviousViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindItem(favoriteMatch: FavoriteMatch) {
            itemView.team_home.text = favoriteMatch.homeTeam
            itemView.team_away.text = favoriteMatch.awayTeam
            itemView.score_home.text = favoriteMatch.homeScore
            itemView.score_away.text = favoriteMatch.awayScore
            if (favoriteMatch.homeScore == null && favoriteMatch.awayScore == null) {
                itemView.strip.text = itemView.resources.getString(R.string.vs)
            } else {
                itemView.strip.text = itemView.resources.getString(R.string.strip)
            }
            val time = favoriteMatch.dateEvent + "\n" + favoriteMatch.time
            itemView.date_match.text = time
        }
    }
}