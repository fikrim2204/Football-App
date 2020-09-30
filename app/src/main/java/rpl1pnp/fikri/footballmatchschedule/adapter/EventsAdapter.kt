package rpl1pnp.fikri.footballmatchschedule.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_match.view.*
import rpl1pnp.fikri.footballmatchschedule.R
import rpl1pnp.fikri.footballmatchschedule.model.Events
import java.text.SimpleDateFormat
import java.util.*

class EventsAdapter(private var events: List<Events>, private val listener: (Events) -> Unit) :
    RecyclerView.Adapter<EventsAdapter.EventViewHolder>() {
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
        @SuppressLint("SetTextI18n")
        fun bindItem(events: Events, listener: (Events) -> Unit) {
            itemView.team_home.text = events.homeTeam
            itemView.team_away.text = events.awayTeam
            itemView.score_home.text = events.homeScore
            itemView.score_away.text = events.awayScore
            if (events.homeScore == null && events.awayScore == null) {
                itemView.strip.text = itemView.resources.getString(R.string.vs)
            } else {
                itemView.strip.text = itemView.resources.getString(R.string.strip)
            }
            val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            df.timeZone = TimeZone.getTimeZone("UTC")
            val date: Date? = df.parse(events.dateEvent + " " + events.time)
            df.timeZone = (TimeZone.getDefault())
            val formattedDate: String? = df.format(date)
            itemView.date.text = formattedDate
            itemView.setOnClickListener {
                listener(events)
            }
        }
    }
}