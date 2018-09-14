package id.gits.kade.ibun.match

import id.gits.kade.ibun.data.Club
import id.gits.kade.ibun.data.Match
import id.gits.kade.ibun.data.source.SportsDataSource
import id.gits.kade.ibun.data.source.SportsRepository

class MatchPresenter(
        private val match: Match,
        private val sportsRepository: SportsRepository,
        private val view: MatchContract.View) : MatchContract.Presenter {

    init {
        view.presenter = this
    }

    override fun start() {
        getClub()
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
            }

            override fun onClubLoaded(club: Club) {
                view.hideLoading()
                view.showClubAway(club)
            }

        })
    }
}