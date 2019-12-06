package rpl1pnp.fikri.footballmatchschedule.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_match.view.*
import rpl1pnp.fikri.footballmatchschedule.R
import rpl1pnp.fikri.footballmatchschedule.model.Events

class EventAdapter(private var events: List<Events>, private val listener: (Events) -> Unit) :
    RecyclerView.Adapter<EventAdapter.EventViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        return EventViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_match,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return events.size
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        return holder.bindItem(events[position], listener)
    }

    class EventViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindItem(events: Events, listener: (Events) -> Unit) {
            itemView.team_home.text = events.homeTeam
            itemView.team_away.text = events.awayTeam
            itemView.score_home.text = events.homeScore
            itemView.score_away.text = events.awayScore
            itemView.date.text = events.dateEvent
            itemView.setOnClickListener {
                listener(events)
            }
        }
    }
}