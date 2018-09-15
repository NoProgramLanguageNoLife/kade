package id.gits.kade.ibun.clubs

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import id.gits.kade.ibun.Club
import id.gits.kade.ibun.R
import id.gits.kade.ibun.club_detail.ClubDetailActivity
import kotlinx.android.synthetic.main.clubs_row.view.*
import org.jetbrains.anko.layoutInflater
import org.jetbrains.anko.startActivity

class ClubsAdapter(val context: Context, var list: ArrayList<Club> = arrayListOf()) : RecyclerView.Adapter<ClubsAdapter.ClubViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClubViewHolder {
        return ClubViewHolder(parent.context.layoutInflater.inflate(R.layout.clubs_row, parent, false))
    }

    override fun onBindViewHolder(holder: ClubViewHolder, position: Int) {
        val club = list[position]
        holder.tvName.text = club.name

        if (club.logo.isNotEmpty())
            Picasso.get()
                    .load(club.logo)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.color.colorError)
                    .resize(100, 100)
                    .into(holder.ivIcon)

        holder.itemView.setOnClickListener {
            context.startActivity<ClubDetailActivity>("club" to club)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ClubViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ivIcon: ImageView = itemView.ivIcon
        var tvName: TextView = itemView.tvName

    }
}