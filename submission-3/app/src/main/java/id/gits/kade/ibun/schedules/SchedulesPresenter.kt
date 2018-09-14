package id.gits.kade.ibun.schedules

import id.gits.kade.ibun.data.Match
import id.gits.kade.ibun.data.source.SportsDataSource
import id.gits.kade.ibun.data.source.SportsRepository

class SchedulesPresenter(
        private val sportsRepository: SportsRepository,
        private val view: SchedulesContract.View
) : SchedulesContract.Presenter {
    private lateinit var type: SchedulesActivity.TYPE

    init {
        view.presenter = this
    }

    override fun setType(type: SchedulesActivity.TYPE) {
        this.type = type
    }

    override fun getMatches() {
        view.showLoading()
        val cb = object : SportsDataSource.LoadMatchesCallback {
            override fun onError(message: String?) {
                view.hideLoading()
                view.showError(message)
            }

            override fun onMatchesLoaded(matches: List<Match>) {
                view.hideLoading()
                view.showMatches(matches)
            }

        }

        when (type) {
            SchedulesActivity.TYPE.PAST -> sportsRepository.getLastMatches(cb)
            SchedulesActivity.TYPE.NEXT -> sportsRepository.getNextMatches(cb)
            SchedulesActivity.TYPE.FAV -> sportsRepository.getFavorited(cb)
        }
    }

    override fun start() {
        getMatches()
    }

}