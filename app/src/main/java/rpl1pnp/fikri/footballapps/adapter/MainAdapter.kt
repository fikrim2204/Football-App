package rpl1pnp.fikri.footballapps.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_main.view.*
import rpl1pnp.fikri.footballapps.R
import rpl1pnp.fikri.footballapps.model.League

class MainAdapter(private val items: List<League>, private val listener: (League) -> Unit) :
    RecyclerView.Adapter<MainAdapter.LeagueTeamHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeagueTeamHolder {
        return LeagueTeamHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.item_main,
                    parent,
                    false
                )
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: LeagueTeamHolder, position: Int) {
        return holder.bindItem(items[position], listener)
    }

    class LeagueTeamHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindItem(league: League, listener: (League) -> Unit) {
            league.photoLeague?.let { Picasso.get().load(it).into(itemView.image_main_league) }
            itemView.text_main_league.text = league.nameLeague
            itemView.setOnClickListener { listener(league) }
        }
    }

}