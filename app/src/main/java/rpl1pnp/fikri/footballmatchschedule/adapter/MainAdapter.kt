package rpl1pnp.fikri.footballmatchschedule.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*
import rpl1pnp.fikri.footballmatchschedule.R
import rpl1pnp.fikri.footballmatchschedule.model.League

class MainAdapter(private val items: List<League>, private val listener: (League) -> Unit) : RecyclerView.Adapter<LeagueTeamHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeagueTeamHolder {
        return LeagueTeamHolder(
            LeagueUI().createView(
                AnkoContext.create(parent.context, parent)
            )
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: LeagueTeamHolder, position: Int) {
        holder.bindItem(items[position], listener)
    }
}

class LeagueUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {
            linearLayout {
                lparams(matchParent, wrapContent)
                padding = dip(16)
                orientation = LinearLayout.HORIZONTAL

                imageView {
                    id = R.id.photo_league
                }.lparams{
                    height = dip(50)
                    width = dip(50)
                }

                textView {
                    id = R.id.name_league
                    textSize = 16f
                }.lparams{
                    margin = dip(15)
                }
            }
        }
    }
}

class LeagueTeamHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val leaguePhoto: ImageView = view.find(R.id.photo_league)
    private val leagueName: TextView = view.find(R.id.name_league)

    fun bindItem(items: League, listener: (League) -> Unit) {
        items.photoLeague?.let { Picasso.get().load(it).fit().into(leaguePhoto) }
        leagueName.text = items.nameLeague
        itemView.setOnClickListener { listener(items)}
    }
}

