package id.gits.kade.ibun.data.source.remote

import android.support.annotation.VisibleForTesting
import id.gits.kade.ibun.BuildConfig
import id.gits.kade.ibun.data.Match
import id.gits.kade.ibun.data.source.SportsDataSource
import id.gits.kade.ibun.utils.EspressoIdlingResource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class SportsRemoteDataSource private constructor(
        private val apiService: SportsService
) : SportsDataSource {
    private fun <T> callback2(success: ((Response<T>) -> Unit)?, failure: ((t: Throwable) -> Unit)? = null): Callback<T> {
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
        EspressoIdlingResource.increment() // App is busy until further notice

        apiService.listPastMatches(BuildConfig.LEAGUE_ID).enqueue(callback2(
                { r ->
                    if (!EspressoIdlingResource.countingIdlingResource.isIdleNow) {
                        EspressoIdlingResource.decrement() // Set app as idle.
                    }
                    r.body()?.let { it -> callback.onMatchesLoaded(it.events.apply { forEach { it.isPast = true } }) }
                },
                { t ->
                    if (!EspressoIdlingResource.countingIdlingResource.isIdleNow) {
                        EspressoIdlingResource.decrement() // Set app as idle.
                    }
                    callback.onError(t.message)
                }))
    }

    override fun getNextMatches(callback: SportsDataSource.LoadMatchesCallback) {
        EspressoIdlingResource.increment() // App is busy until further notice
        apiService.listNextMatches(BuildConfig.LEAGUE_ID).enqueue(callback2(
                { r ->
                    if (!EspressoIdlingResource.countingIdlingResource.isIdleNow) {
                        EspressoIdlingResource.decrement() // Set app as idle.
                    }
                    r.body()?.let { callback.onMatchesLoaded(it.events) }
                },
                { t ->
                    if (!EspressoIdlingResource.countingIdlingResource.isIdleNow) {
                        EspressoIdlingResource.decrement() // Set app as idle.
                    }
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

    override fun isFavorited(match: Match, callback: SportsDataSource.CheckFavoriteCallback) {
        // not implemented, local data source only
    }

    override fun deleteAllFavorites() {
        // not implemented, local data source only
    }

    override fun getFavorited(callback: SportsDataSource.LoadMatchesCallback) {
        // not implemented, local data source only
    }

    override fun saveFavorite(match: Match, callback: SportsDataSource.ToggleFavoriteCallback) {
        // not implemented, local data source only
    }

    override fun deleteFavorite(match: Match, callback: SportsDataSource.ToggleFavoriteCallback) {
        // not implemented, local data source only
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

            // I am sure it will not be null
            return INSTANCE!!
        }

        @VisibleForTesting
        fun clearInstance() {
            INSTANCE = null
        }
    }

}