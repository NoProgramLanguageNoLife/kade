package id.gits.kade.ibun.data.source

import id.gits.kade.ibun.data.source.remote.SportsRemoteDataSource

class SportsRepository(
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

    companion object {

        private var INSTANCE: SportsRepository? = null

        /**
         * Returns the single instance of this class, creating it if necessary.
         * @param sportsRemoteDataSource the backend data source
         * *
         * @return the [SportsRepository] instance
         */
        @JvmStatic
        fun getInstance(sportsRemoteDataSource: SportsRemoteDataSource): SportsRepository {
            return INSTANCE ?: SportsRepository(sportsRemoteDataSource)
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