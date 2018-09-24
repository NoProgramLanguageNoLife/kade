package id.gits.kade.ibun.main.teams

import id.gits.kade.ibun.data.League
import id.gits.kade.ibun.data.Team
import id.gits.kade.ibun.data.source.SportsDataSource
import id.gits.kade.ibun.data.source.SportsRepository

class TeamsPresenter(
        private val sportsRepository: SportsRepository,
        private val view: TeamsContract.View

) : TeamsContract.Presenter {
    private lateinit var type: TeamsContract.TYPE

    private lateinit var leagueId: String

    init {
        view.presenter = this
    }

    override fun setType(type: TeamsContract.TYPE) {
        this.type = type
    }

    override fun getLeagues() {
        sportsRepository.getLeagues(object : SportsDataSource.LoadLeaguesCallback {
            override fun onError(message: String?) {
                view.showError(message)
            }

            override fun onLeaguesLoaded(leagues: List<League>) {
                view.showLeagues(leagues)
            }

        })
    }

    override fun getTeams() {
        view.showLoading()
        val cb = object : SportsDataSource.LoadTeamsCallback {
            override fun onError(message: String?) {
                view.hideLoading()
                view.showError(message)
            }

            override fun onTeamsLoaded(teams: List<Team>) {
                view.hideLoading()
                view.showTeams(teams)
            }

        }

        if (type == TeamsContract.TYPE.NORMAL) sportsRepository.getTeams(leagueId, cb)
        else if (type == TeamsContract.TYPE.FAV) sportsRepository.getFavoriteTeams(cb)
    }

    override fun setLeague(leagueId: String) {
        this.leagueId = leagueId
    }

    override fun start() {
        if (type != TeamsContract.TYPE.FAV)
            getLeagues()
        getTeams()
    }


}
