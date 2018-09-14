package id.gits.kade.ibun.schedules

import id.gits.kade.ibun.BasePresenter
import id.gits.kade.ibun.BaseView
import id.gits.kade.ibun.data.Match

/**
 * This specifies the contract between the view and the presenter.
 */
interface SchedulesContract {

    interface View : BaseView<Presenter> {
        fun showMatches(matches: List<Match>)
        fun showLoading()
        fun hideLoading()
        fun showMatchDetailUi(match: Match)
    }

    interface Presenter : BasePresenter {
        fun setType(type: SchedulesActivity.TYPE)
        fun getMatches()
    }
}