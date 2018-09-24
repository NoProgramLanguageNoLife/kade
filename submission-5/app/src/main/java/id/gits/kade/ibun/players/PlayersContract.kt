package id.gits.kade.ibun.players

import id.gits.kade.ibun.BasePresenter
import id.gits.kade.ibun.BaseView
import id.gits.kade.ibun.data.Player
import id.gits.kade.ibun.data.Team

/**
 * This specifies the contract between the view and the presenter.
 */
interface PlayersContract {

    interface View : BaseView<Presenter> {
        fun showPlayers(players: List<Player>)
        fun showLoading()
        fun hideLoading()
        fun showPlayerDetailUi(player: Player)
    }

    interface Presenter : BasePresenter {
        fun setTeam(team: Team)
        fun getPlayers()
    }
}