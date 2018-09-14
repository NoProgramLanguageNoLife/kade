package id.gits.kade.ibun

interface BaseView<T> {

    var presenter: T
    fun showError(message:String?)

}