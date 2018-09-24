package id.gits.kade.ibun.main.matches

import id.gits.kade.ibun.BasePresenter
import id.gits.kade.ibun.BaseView
import id.gits.kade.ibun.data.League
import id.gits.kade.ibun.data.Match
import id.gits.kade.ibun.main.MainActivity

/**
 * This specifies the contract between the view and the presenter.
 */
interface MatchesContract {

    interface View : BaseView<Presenter> {
        fun showMatches(matches: List<Match>)
        fun showLeagues(leagues: List<League>)
        fun showLoading()
        fun hideLoading()
        fun showMatchDetailUi(match: Match)
    }

    interface Presenter : BasePresenter {
        fun setType(type: MainActivity.TYPE)
        fun getMatches()
        fun getLeagues()
        fun setLeague(leagueId: String)
    }
}