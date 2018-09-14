package id.gits.kade.ibun.data.source.remote

import id.gits.kade.ibun.data.ScheduleDao
import id.gits.kade.ibun.data.TeamDao
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SportsService {
    @GET("eventspastleague.php")
    fun listPastMatches(@Query("id") leagueId: String): Call<ScheduleDao>

    @GET("eventsnextleague.php")
    fun listNextMatches(@Query("id") leagueId: String): Call<ScheduleDao>

    @GET("lookupteam.php")
    fun clubDetail(@Query("id") clubId: String): Call<TeamDao>
}