package id.gits.kade.ibun.schedules

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import id.gits.kade.ibun.R
import id.gits.kade.ibun.data.Match
import kotlinx.android.synthetic.main.schedules_row.view.*
import org.jetbrains.anko.layoutInflater

class SchedulesAdapter(var list: List<Match>, private val itemListener: ScheduleItemListener) : RecyclerView.Adapter<SchedulesAdapter.ClubViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClubViewHolder {
        return ClubViewHolder(parent.context.layoutInflater.inflate(R.layout.schedules_row, parent, false))
    }

    override fun onBindViewHolder(holder: ClubViewHolder, position: Int) {
        val match = list[position]
        holder.tvHomeName.text = match.strHomeTeam
        holder.tvAwayName.text = match.strAwayTeam

        if (match.isPast) {
            holder.tvHomeScore.text = String.format(match.intHomeScore.toString())
            holder.tvAwayScore.text = String.format(match.intAwayScore.toString())
        } else {
            holder.tvHomeScore.visibility = View.GONE
            holder.tvAwayScore.visibility = View.GONE
        }

        holder.tvDate.text = match.toFormattedDate()

        holder.itemView.setOnClickListener {
            itemListener.onMatchClick(match)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ClubViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tvDate: TextView = itemView.tvDate
        var tvHomeName: TextView = itemView.tvHomeName
        var tvHomeScore: TextView = itemView.tvHomeScore
        var tvAwayName: TextView = itemView.tvAwayName
        var tvAwayScore: TextView = itemView.tvAwayScore

    }

    interface ScheduleItemListener {
        fun onMatchClick(clickedMatch: Match)
    }
}