package id.gits.kade.ibun.player

import android.os.Bundle
import id.gits.kade.ibun.BasePresenter
import id.gits.kade.ibun.BaseView
import id.gits.kade.ibun.data.Player

/**
 * This specifies the contract between the view and the presenter.
 */
interface PlayerContract {
    interface View : BaseView<Presenter> {
        fun showPlayer(player: Player)
        fun showLoading()
        fun hideLoading()
    }

    interface Presenter : BasePresenter {
        var player: Player

        fun setPlayerFromBundle(bundle: Bundle?)

        fun getPlayer()
    }
}