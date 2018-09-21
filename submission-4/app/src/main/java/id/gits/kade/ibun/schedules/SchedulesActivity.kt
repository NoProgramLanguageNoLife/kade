package id.gits.kade.ibun.schedules

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.support.annotation.VisibleForTesting
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.widget.LinearLayout
import androidx.test.espresso.IdlingResource
import id.gits.kade.ibun.Injection
import id.gits.kade.ibun.R
import id.gits.kade.ibun.utils.EspressoIdlingResource
import id.gits.kade.ibun.utils.replaceFragmentInActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.design.bottomNavigationView

class SchedulesActivity : AppCompatActivity() {

    private val menuPrev = 1
    private val menuNext = 2
    private val menuFav = 3

    private val fragmentMap: HashMap<String, SchedulesFragment> = HashMap()

    val countingIdlingResource: IdlingResource
        @VisibleForTesting
        get() = EspressoIdlingResource.countingIdlingResource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ScheduleActivityUI().setContentView(this)

        initBottomBar()
    }

    private fun initBottomBar() {
        with(findViewById<BottomNavigationView>(ScheduleActivityUI.navigationView)) {
            menu.add(Menu.NONE, menuPrev, Menu.NONE, R.string.menu_prev).setIcon(R.drawable.ic_baseline_replay)
            menu.add(Menu.NONE, menuNext, Menu.NONE, R.string.menu_next).setIcon(R.drawable.ic_baseline_calendar)
            menu.add(Menu.NONE, menuFav, Menu.NONE, R.string.menu_fav).setIcon(R.drawable.ic_baseline_favorites)

            setOnNavigationItemSelectedListener {
                val selectedFragment: SchedulesFragment?
                when (it.itemId) {
                    menuPrev -> {
                        selectedFragment = fragmentMap["prev"] ?: SchedulesFragment.newInstance(TYPE.PAST)
                                .apply {
                                    fragmentMap["prev"] = this
                                    SchedulesPresenter(Injection.provideSportsRepository(ctx), this)
                                }
                    }
                    menuNext -> {
                        selectedFragment = fragmentMap["next"] ?: SchedulesFragment.newInstance(TYPE.NEXT)
                                .apply {
                                    fragmentMap["next"] = this
                                    SchedulesPresenter(Injection.provideSportsRepository(ctx), this)
                                }
                    }
                    menuFav -> {
                        selectedFragment = fragmentMap["fav"] ?: SchedulesFragment.newInstance(TYPE.FAV)
                                .apply {
                                    fragmentMap["fav"] = this
                                    SchedulesPresenter(Injection.provideSportsRepository(ctx), this)
                                }
                    }
                    else -> selectedFragment = null
                }

                if (selectedFragment != null) {
                    replaceFragmentInActivity(selectedFragment, ScheduleActivityUI.contentFrame)
                    true
                } else {
                    false
                }
            }

            // create initial fragment for the first time only
            SchedulesFragment.newInstance(TYPE.PAST).apply {
                replaceFragmentInActivity(this, ScheduleActivityUI.contentFrame)
                SchedulesPresenter(Injection.provideSportsRepository(ctx), this)
            }
        }
    }

    class ScheduleActivityUI : AnkoComponent<SchedulesActivity> {
        companion object {
            const val contentFrame = 1
            const val navigationView = 2
        }

        override fun createView(ui: AnkoContext<SchedulesActivity>) = with(ui) {
            linearLayout {
                orientation = LinearLayout.VERTICAL

                frameLayout {
                    id = contentFrame
                }.lparams(width = LinearLayout.LayoutParams.MATCH_PARENT, height = 0, weight = 1F)

                bottomNavigationView {
                    id = navigationView
                    backgroundColorResource = R.color.colorPrimary

                    val states = arrayOf(
                            intArrayOf(android.R.attr.state_selected),
                            intArrayOf(android.R.attr.state_enabled)
                    )

                    val colors = intArrayOf(Color.WHITE, Color.DKGRAY)

                    val colorStateList = ColorStateList(states, colors)
                    itemTextColor = colorStateList
                    itemIconTintList = colorStateList

                }.lparams(width = LinearLayout.LayoutParams.MATCH_PARENT)
            }
        }

    }

    enum class TYPE {
        PAST, NEXT, FAV
    }
}

