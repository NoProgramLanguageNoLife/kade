package id.gits.kade.ibun.players

import id.gits.kade.ibun.data.Player
import id.gits.kade.ibun.data.Team
import id.gits.kade.ibun.data.source.SportsDataSource
import id.gits.kade.ibun.data.source.SportsRepository

class PlayersPresenter(
        private val sportsRepository: SportsRepository,
        private val view: PlayersContract.View

) : PlayersContract.Presenter {
    private lateinit var team: Team

    init {
        view.presenter = this
    }

    override fun setTeam(team: Team) {
        this.team = team
    }

    override fun getPlayers() {
        view.showLoading()
        val cb = object : SportsDataSource.LoadPlayersCallback {
            override fun onError(message: String?) {
                view.hideLoading()
                view.showError(message)
            }

            override fun onPlayersLoaded(players: List<Player>) {
                view.hideLoading()
                view.showPlayers(players)
            }

        }

        sportsRepository.getPlayers(team.idTeam, cb)
    }

    override fun start() {
        getPlayers()
    }


}
