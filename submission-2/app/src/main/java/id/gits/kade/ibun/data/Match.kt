package id.gits.kade.ibun.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.text.SimpleDateFormat
import java.util.*

@Parcelize
data class Match(
        val idHomeTeam: String, val idAwayTeam: String, val strHomeTeam: String, val strAwayTeam: String,
        val intHomeScore: Int?, val intAwayScore: Int?, val strDate: String, val strTime: String,
        val strHomeFormation: String?, val strAwayFormation: String?,
        val strHomeGoalDetails: String?, val strAwayGoalDetails: String?,
        val intHomeShots: Int?, val intAwayShots: Int?,
        val strHomeLineupGoalkeeper: String?, val strHomeLineupDefense: String?,
        val strHomeLineupMidfield: String?, val strHomeLineupForward: String?, val strHomeLineupSubstitutes: String?,
        val strAwayLineupGoalkeeper: String?, val strAwayLineupDefense: String?,
        val strAwayLineupMidfield: String?, val strAwayLineupForward: String?, val strAwayLineupSubstitutes: String?,
        var isPast: Boolean) : Parcelable {

    fun toFormattedDate(): String {
        val dateEvent = SimpleDateFormat("dd/MM/yy HH:mm:ssXXX", Locale.ENGLISH).parse("${strDate} ${strTime}")
        return SimpleDateFormat("EEEE, dd MMM yyyy - HH:mm", Locale.getDefault()).format(dateEvent)
    }
}