package rpl1pnp.fikri.footballapps.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_match.view.*
import rpl1pnp.fikri.footballapps.R
import rpl1pnp.fikri.footballapps.model.Event
import java.text.SimpleDateFormat
import java.util.*

class EventsAdapter(private var events: List<Event>, private val listener: (Event) -> Unit) :
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

    override fun getItemCount() = events.size

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        return holder.bindItem(events[position], listener)
    }

    class EventViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindItem(event: Event, listener: (Event) -> Unit) {
            itemView.team_home.text = event.homeTeam
            itemView.team_away.text = event.awayTeam
            itemView.score_home.text = event.homeScore
            itemView.score_away.text = event.awayScore
            if (event.homeScore == null && event.awayScore == null) {
                itemView.strip.text = itemView.resources.getString(R.string.vs)
            } else {
                itemView.strip.text = itemView.resources.getString(R.string.strip)
            }
            val df = SimpleDateFormat("yyyy-MM-dd\nHH:mm:ss", Locale.getDefault())
            df.timeZone = TimeZone.getTimeZone("UTC")
            val date: Date? = df.parse(event.dateEvent + "\n" + event.time)
            df.timeZone = (TimeZone.getDefault())
            val formattedDate: String? = df.format(date!!)
            itemView.date_match.text = formattedDate
            itemView.setOnClickListener {
                listener(event)
            }
        }
    }
}