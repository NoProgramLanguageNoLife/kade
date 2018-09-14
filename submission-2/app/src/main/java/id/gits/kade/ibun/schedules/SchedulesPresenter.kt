package id.gits.kade.ibun.schedules

import id.gits.kade.ibun.data.Match
import id.gits.kade.ibun.data.source.SportsDataSource
import id.gits.kade.ibun.data.source.SportsRepository

class SchedulesPresenter(
        private val sportsRepository: SportsRepository,
        private val view: SchedulesContract.View
) : SchedulesContract.Presenter {
    private var isPast: Boolean = false

    init {
        view.presenter = this
    }

    override fun setMode(isPast: Boolean) {
        this.isPast = isPast
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

        if (isPast)
            sportsRepository.getLastMatches(cb)
        else
            sportsRepository.getNextMatches(cb)
    }

    override fun start() {
        getMatches()
    }

}