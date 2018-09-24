package id.gits.kade.ibun.team

import id.gits.kade.ibun.BasePresenter
import id.gits.kade.ibun.BaseView
import id.gits.kade.ibun.data.Team

/**
 * This specifies the contract between the view and the presenter.
 */
interface TeamContract {
    interface View : BaseView<Presenter> {
        fun showTeam(team: Team)
        fun showLoading()
        fun hideLoading()
        fun showAddFavoriteSuccess()
        fun showRemoveFavoriteSuccess()
        fun showToggleFavoriteError()
        fun invalidateMenu()
    }

    interface Presenter : BasePresenter {
        fun getTeam()
        fun isFavorite(): Boolean
        fun addToFavorite()
        fun removeFromFavorite()
    }
}