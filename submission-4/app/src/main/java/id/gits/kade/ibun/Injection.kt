package id.gits.kade.ibun

import android.content.Context
import id.gits.kade.ibun.data.source.SportsDataSource
import id.gits.kade.ibun.data.source.SportsRepository
import id.gits.kade.ibun.data.source.local.SportsLocalDataSource
import id.gits.kade.ibun.data.source.remote.SportsRemoteDataSource


/**
 * Enables injection of production implementations for
 * [SportsDataSource] at compile time.
 */
object Injection {
    fun provideSportsRepository(ctx:Context): SportsRepository {
        return SportsRepository.getInstance(SportsLocalDataSource.getInstance(ctx),
                SportsRemoteDataSource.getInstance())
    }
}
