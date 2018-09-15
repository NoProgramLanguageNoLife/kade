package id.gits.kade.ibun.data.source.remote

import android.support.annotation.VisibleForTesting
import id.gits.kade.ibun.BuildConfig
import id.gits.kade.ibun.data.source.SportsDataSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class SportsRemoteDataSource private constructor(
        val apiService: SportsService
) : SportsDataSource {
    fun <T> callback2(success: ((Response<T>) -> Unit)?, failure: ((t: Throwable) -> Unit)? = null): Callback<T> {
        return object : Callback<T> {
            override fun onResponse(call: Call<T>, response: retrofit2.Response<T>) {
                success?.invoke(response)
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                failure?.invoke(t)
            }
        }
    }

    override fun getLastMatches(callback: SportsDataSource.LoadMatchesCallback) {
        apiService.listPastMatches(BuildConfig.LEAGUE_ID).enqueue(callback2(
                { r ->
                    r.body()?.let { callback.onMatchesLoaded(it.events.apply { forEach { it.isPast = true } }) }
                },
                { t ->
                    callback.onError(t.message)
                }))
    }

    override fun getNextMatches(callback: SportsDataSource.LoadMatchesCallback) {
        apiService.listNextMatches(BuildConfig.LEAGUE_ID).enqueue(callback2(
                { r ->
                    r.body()?.let { callback.onMatchesLoaded(it.events) }
                },
                { t ->
                    callback.onError(t.message)
                }))
    }

    override fun getClub(clubId: String, callback: SportsDataSource.GetClubCallback) {
        apiService.clubDetail(clubId).enqueue(callback2(
                { r ->
                    r.body()?.let {
                        if (it.teams != null)
                            callback.onClubLoaded(it.teams[0])
                        else
                            callback.onError("Team not found")
                    }
                },
                { t ->
                    callback.onError(t.message)
                }))
    }

    companion object {
        private var INSTANCE: SportsRemoteDataSource? = null

        @JvmStatic
        fun getInstance(): SportsRemoteDataSource {
            if (INSTANCE == null) {
                synchronized(SportsRemoteDataSource::javaClass) {
                    val retrofit = Retrofit.Builder()
                            .addConverterFactory(GsonConverterFactory.create())
                            .baseUrl(BuildConfig.BASE_API_URL)
                            .build()

                    INSTANCE = SportsRemoteDataSource(retrofit.create(SportsService::class.java))
                }
            }
            return INSTANCE!!
        }

        @VisibleForTesting
        fun clearInstance() {
            INSTANCE = null
        }
    }

}