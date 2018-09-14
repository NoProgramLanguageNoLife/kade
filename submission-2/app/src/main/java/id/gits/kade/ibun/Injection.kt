package id.gits.kade.ibun

import id.gits.kade.ibun.data.source.SportsDataSource
import id.gits.kade.ibun.data.source.SportsRepository
import id.gits.kade.ibun.data.source.remote.SportsRemoteDataSource


/**
 * Enables injection of production implementations for
 * [SportsDataSource] at compile time.
 */
object Injection {
    fun provideSportsRepository(): SportsRepository {
        return SportsRepository.getInstance(SportsRemoteDataSource.getInstance())
    }
}
