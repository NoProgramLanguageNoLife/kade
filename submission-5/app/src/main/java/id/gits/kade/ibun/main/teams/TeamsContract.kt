package id.gits.kade.ibun.main.teams

import id.gits.kade.ibun.BasePresenter
import id.gits.kade.ibun.BaseView
import id.gits.kade.ibun.data.League
import id.gits.kade.ibun.data.Team

/**
 * This specifies the contract between the view and the presenter.
 */
interface TeamsContract {

    interface View : BaseView<Presenter> {
        fun showLeagues(leagues: List<League>)
        fun showTeams(teams: List<Team>)
        fun showLoading()
        fun hideLoading()
        fun showTeamDetailUi(team: Team)
    }

    interface Presenter : BasePresenter {
        fun setType(type: TYPE)
        fun setLeague(leagueId: String)
        fun getLeagues()
        fun getTeams()
    }

    enum class TYPE {
        NORMAL, FAV
    }
}