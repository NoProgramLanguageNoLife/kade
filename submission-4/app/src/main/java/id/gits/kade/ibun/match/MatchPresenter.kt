package id.gits.kade.ibun.match

import id.gits.kade.ibun.data.Club
import id.gits.kade.ibun.data.Match
import id.gits.kade.ibun.data.source.SportsDataSource
import id.gits.kade.ibun.data.source.SportsRepository

class MatchPresenter(
        private val match: Match,
        private val sportsRepository: SportsRepository,
        private val view: MatchContract.View) : MatchContract.Presenter {

    private var isFavorite: Boolean = false

    init {
        view.presenter = this
    }

    override fun start() {
        getClub()
        checkIsFavorite()
    }

    override fun getClub() {
        view.showLoading()
        sportsRepository.getClub(match.idHomeTeam, object : SportsDataSource.GetClubCallback {
            override fun onError(message: String?) {
                view.hideLoading()
                view.showError(message)
            }

            override fun onClubLoaded(club: Club) {
                view.hideLoading()
                view.showClubHome(club)
            }

        })

        sportsRepository.getClub(match.idAwayTeam, object : SportsDataSource.GetClubCallback {
            override fun onError(message: String?) {
                view.hideLoading()
                view.showError(message)
            }

            override fun onClubLoaded(club: Club) {
                view.hideLoading()
                view.showClubAway(club)
            }

        })
    }

    private fun checkIsFavorite(){
        sportsRepository.isFavorited(match, object : SportsDataSource.CheckFavoriteCallback {
            override fun onError(message: String?) {
                view.showError(message)
            }

            override fun onCheckedFavorited(isFavoritedNow: Boolean) {
                isFavorite = isFavoritedNow
                view.invalidateMenu()
            }

        })
    }

    override fun isFavorite(): Boolean {
        return isFavorite
    }

    override fun removeFromFavorite() {
        sportsRepository.deleteFavorite(match,  object : SportsDataSource.ToggleFavoriteCallback {
            override fun onError(message: String?) {
                view.showToggleFavoriteError()
            }

            override fun onToggleSuccess(match: Match, isFavoritedNow: Boolean) {
                view.showRemoveFavoriteSuccess()
                isFavorite = isFavoritedNow
                view.invalidateMenu()
            }

        })
    }

    override fun addToFavorite() {
        sportsRepository.saveFavorite(match,  object : SportsDataSource.ToggleFavoriteCallback {
            override fun onError(message: String?) {
                view.showToggleFavoriteError()
            }

            override fun onToggleSuccess(match: Match, isFavoritedNow: Boolean) {
                view.showAddFavoriteSuccess()
                isFavorite = isFavoritedNow
                view.invalidateMenu()
            }

        })
    }
}