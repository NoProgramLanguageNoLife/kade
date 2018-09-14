package id.gits.kade.ibun.data.source.local

import android.content.Context
import android.support.annotation.VisibleForTesting
import com.google.gson.Gson
import id.gits.kade.ibun.data.Match
import id.gits.kade.ibun.data.source.SportsDataSource
import org.jetbrains.anko.db.*

class SportsLocalDataSource private constructor(
        val db: MyDatabaseOpenHelper
) : SportsDataSource {
    override fun getLastMatches(callback: SportsDataSource.LoadMatchesCallback) {
        // not implemented, not stored in local db
    }

    override fun getNextMatches(callback: SportsDataSource.LoadMatchesCallback) {
        // not implemented, not stored in local db
    }

    override fun getClub(clubId: String, callback: SportsDataSource.GetClubCallback) {
        // not implemented, not stored in local db
    }

    override fun isFavorited(match: Match, callback: SportsDataSource.CheckFavoriteCallback) {
        db.use {
            select(FavoriteDao.TABLE_FAVORITE).whereArgs("${FavoriteDao.MATCH_ID} = {matchId}",
                    "matchId" to match.idEvent).exec {
                callback.onCheckedFavorited(this.count > 0)
            }
        }
    }

    override fun saveFavorite(match: Match, callback: SportsDataSource.ToggleFavoriteCallback) {
        db.use {
            insert(FavoriteDao.TABLE_FAVORITE,
                    FavoriteDao.MATCH_ID to match.idEvent,
                    FavoriteDao.JSON to Gson().toJson(match),
                    FavoriteDao.IS_PAST to if (match.isPast) 0 else 1
            )
            callback.onToggleSuccess(match, true)
        }
    }

    override fun deleteFavorite(match: Match, callback: SportsDataSource.ToggleFavoriteCallback) {
        db.use {
            delete(FavoriteDao.TABLE_FAVORITE, "${FavoriteDao.MATCH_ID} = {matchId}",
                    "matchId" to match.idEvent)
            callback.onToggleSuccess(match, false)
        }
    }

    override fun getFavorited(callback: SportsDataSource.LoadMatchesCallback) {
        db.use {
            select(FavoriteDao.TABLE_FAVORITE).exec {
                val matches = this.parseList(MyRowParser())
                callback.onMatchesLoaded(matches)
            }
        }
    }

    companion object {
        private var INSTANCE: SportsLocalDataSource? = null

        @JvmStatic
        fun getInstance(ctx: Context): SportsLocalDataSource {
            if (INSTANCE == null) {
                synchronized(SportsLocalDataSource::javaClass) {
                    INSTANCE = SportsLocalDataSource(ctx.database)
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


    class MyRowParser : MapRowParser<Match> {
        override fun parseRow(columns: Map<String, Any?>): Match {
            val json: String? = columns[FavoriteDao.JSON].toString()
            return Gson().fromJson<Match>(json, Match::class.java)
        }

    }

}