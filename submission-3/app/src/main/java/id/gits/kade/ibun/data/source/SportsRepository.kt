package id.gits.kade.ibun.data.source

import id.gits.kade.ibun.data.Match
import id.gits.kade.ibun.data.source.local.SportsLocalDataSource
import id.gits.kade.ibun.data.source.remote.SportsRemoteDataSource

class SportsRepository(
        val sportsLocalDataSource: SportsDataSource,
        val sportsRemoteDataSource: SportsDataSource
) : SportsDataSource {
    override fun getLastMatches(callback: SportsDataSource.LoadMatchesCallback) {
        sportsRemoteDataSource.getLastMatches(callback)
    }

    override fun getNextMatches(callback: SportsDataSource.LoadMatchesCallback) {
        sportsRemoteDataSource.getNextMatches(callback)
    }

    override fun getClub(clubId: String, callback: SportsDataSource.GetClubCallback) {
        sportsRemoteDataSource.getClub(clubId, callback)
    }

    override fun isFavorited(match: Match, callback: SportsDataSource.CheckFavoriteCallback) {
        sportsLocalDataSource.isFavorited(match, callback)
    }

    override fun saveFavorite(match: Match, callback: SportsDataSource.ToggleFavoriteCallback) {
        sportsLocalDataSource.saveFavorite(match, callback)
    }

    override fun deleteFavorite(match: Match, callback: SportsDataSource.ToggleFavoriteCallback) {
        sportsLocalDataSource.deleteFavorite(match, callback)
    }

    override fun getFavorited(callback: SportsDataSource.LoadMatchesCallback) {
        sportsLocalDataSource.getFavorited(callback)
    }

    companion object {

        private var INSTANCE: SportsRepository? = null

        /**
         * Returns the single instance of this class, creating it if necessary.
         * @param sportsLocalDataSource the local data source
         * @param sportsRemoteDataSource the backend data source
         * *
         * @return the [SportsRepository] instance
         */
        @JvmStatic
        fun getInstance(sportsLocalDataSource: SportsLocalDataSource, sportsRemoteDataSource: SportsRemoteDataSource): SportsRepository {
            return INSTANCE ?: SportsRepository(sportsLocalDataSource, sportsRemoteDataSource)
                    .apply { INSTANCE = this }
        }

        /**
         * Used to force [getInstance] to create a new instance
         * next time it's called.
         */
        @JvmStatic
        fun destroyInstance() {
            INSTANCE = null
        }
    }

}