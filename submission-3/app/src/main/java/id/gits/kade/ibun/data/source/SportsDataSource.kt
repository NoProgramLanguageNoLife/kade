package id.gits.kade.ibun.data.source

import id.gits.kade.ibun.data.Club
import id.gits.kade.ibun.data.Match

interface SportsDataSource {
    interface BaseCallback {

        fun onError(message: String?)
    }

    interface LoadMatchesCallback : BaseCallback {
        fun onMatchesLoaded(matches: List<Match>)
    }

    interface GetClubCallback : BaseCallback {
        fun onClubLoaded(club: Club)
    }

    interface ToggleFavoriteCallback : BaseCallback {
        fun onToggleSuccess(match: Match, isFavoritedNow: Boolean)
    }

    interface CheckFavoriteCallback : BaseCallback {
        fun onCheckedFavorited(isFavoritedNow: Boolean)
    }

    fun getLastMatches(callback: LoadMatchesCallback)

    fun getNextMatches(callback: LoadMatchesCallback)

    fun getClub(clubId: String, callback: GetClubCallback)

    fun getFavorited(callback: LoadMatchesCallback)

    fun saveFavorite(match: Match, callback: ToggleFavoriteCallback)

    fun deleteFavorite(match: Match, callback: ToggleFavoriteCallback)

    fun isFavorited(match: Match, callback: CheckFavoriteCallback)
}
