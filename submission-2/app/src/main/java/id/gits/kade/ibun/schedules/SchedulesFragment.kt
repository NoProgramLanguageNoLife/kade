package id.gits.kade.ibun.schedules

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.gits.kade.ibun.data.Match
import id.gits.kade.ibun.match.MatchActivity
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.swipeRefreshLayout
import org.jetbrains.anko.support.v4.toast

class SchedulesFragment : Fragment(), SchedulesContract.View {
    override lateinit var presenter: SchedulesContract.Presenter

    lateinit var swipeLayout: SwipeRefreshLayout

    val items: ArrayList<Match> = arrayListOf()

    lateinit var listAdapter: SchedulesAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = ScheduleFragmentUI().createView(AnkoContext.create(ctx, this))

        listAdapter = SchedulesAdapter(items, object : SchedulesAdapter.ScheduleItemListener {
            override fun onMatchClick(clickedMatch: Match) {
                showMatchDetailUi(clickedMatch)
            }
        })

        with(view.findViewById<RecyclerView>(ScheduleFragmentUI.recyclerViewId)) {
            adapter = listAdapter
        }

        with(view.findViewById<SwipeRefreshLayout>(ScheduleFragmentUI.swipeRefreshId)) {
            swipeLayout = this
            setOnRefreshListener { presenter.getMatches() }
        }

        arguments?.getBoolean(ARGUMENT_IS_PAST)?.let { presenter.setMode(it) }
        presenter.start()

        return view
    }

    override fun showError(message: String?) {
        message?.let { toast(it) }
    }

    override fun showMatches(matches: List<Match>) {
        items.clear()
        items.addAll(matches)
        listAdapter.notifyDataSetChanged()
    }

    override fun showLoading() {
        swipeLayout.isRefreshing = true
    }

    override fun hideLoading() {
        swipeLayout.isRefreshing = false
    }

    override fun showMatchDetailUi(match: Match) {
        context?.startActivity<MatchActivity>(MatchActivity.EXTRA_MATCH to match)
    }

    companion object {

        private const val ARGUMENT_IS_PAST = "IS_PAST"

        fun newInstance(isPast: Boolean) =
                SchedulesFragment().apply {
                    arguments = Bundle().apply { putBoolean(ARGUMENT_IS_PAST, isPast) }
                }
    }

    class ScheduleFragmentUI : AnkoComponent<Fragment> {
        companion object {
            val swipeRefreshId = 1
            val recyclerViewId = 2
        }

        override fun createView(ui: AnkoContext<Fragment>) = with(ui) {
            swipeRefreshLayout {
                id = swipeRefreshId

                recyclerView {
                    id = recyclerViewId
                    layoutManager = LinearLayoutManager(context)
                }
            }
        }
    }
}