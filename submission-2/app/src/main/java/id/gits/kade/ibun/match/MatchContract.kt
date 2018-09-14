package id.gits.kade.ibun.match

import id.gits.kade.ibun.BasePresenter
import id.gits.kade.ibun.BaseView
import id.gits.kade.ibun.data.Club

/**
 * This specifies the contract between the view and the presenter.
 */
interface MatchContract {
    interface View : BaseView<Presenter> {
        fun showClubHome(club: Club)
        fun showClubAway(club: Club)
        fun showLoading()
        fun hideLoading()
    }

    interface Presenter : BasePresenter {
        fun getClub()
    }
}