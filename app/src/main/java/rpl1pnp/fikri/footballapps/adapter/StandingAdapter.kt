package rpl1pnp.fikri.footballapps.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_standing.view.*
import rpl1pnp.fikri.footballapps.R
import rpl1pnp.fikri.footballapps.adapter.StandingAdapter.TableViewHolder
import rpl1pnp.fikri.footballapps.model.Table

class StandingAdapter(private var table: List<Table>) :
    RecyclerView.Adapter<TableViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TableViewHolder {
        return TableViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_standing, parent, false)
        )
    }

    override fun onBindViewHolder(holderTable: TableViewHolder, position: Int) {
        return holderTable.bindItem(table[position], position)
    }

    override fun getItemCount() = table.size

    class TableViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindItem(table: Table, position: Int) {
            itemView.tv_no_table.text = (position + 1).toString()
            itemView.tv_team_table.text = table.name
            itemView.tv_goal_for_table.text = table.goalsfor.toString()
            itemView.tv_goal_against_table.text = table.goalsagainst.toString()
            itemView.tv_goal_difference_table.text = table.goalsdifference.toString()
            itemView.tv_win_table.text = table.win.toString()
            itemView.tv_draw_table.text = table.draw.toString()
            itemView.tv_loss_table.text = table.loss.toString()
            itemView._tv_total_table.text = table.total.toString()
        }

    }
}