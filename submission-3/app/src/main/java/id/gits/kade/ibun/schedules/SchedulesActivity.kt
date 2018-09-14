package id.gits.kade.ibun.schedules

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.widget.LinearLayout
import id.gits.kade.ibun.Injection
import id.gits.kade.ibun.R
import id.gits.kade.ibun.utils.replaceFragmentInActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.design.bottomNavigationView

class SchedulesActivity : AppCompatActivity() {

    private val MENU_PREV = 1
    private val MENU_NEXT = 2
    private val MENU_FAV = 3

    private val fragmentMap: HashMap<String, SchedulesFragment> = HashMap()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ScheduleActivityUI().setContentView(this)

        initBottomBar()
    }

    private fun initBottomBar() {
        with(findViewById<BottomNavigationView>(ScheduleActivityUI.navigationView)) {
            menu.add(Menu.NONE, MENU_PREV, Menu.NONE, R.string.menu_prev).setIcon(R.drawable.ic_baseline_replay)
            menu.add(Menu.NONE, MENU_NEXT, Menu.NONE, R.string.menu_next).setIcon(R.drawable.ic_baseline_calendar)
            menu.add(Menu.NONE, MENU_FAV, Menu.NONE, R.string.menu_fav).setIcon(R.drawable.ic_baseline_favorites)

            setOnNavigationItemSelectedListener {
                val selectedFragment: SchedulesFragment?
                when (it.itemId) {
                    MENU_PREV -> {
                        selectedFragment = fragmentMap.get("prev") ?: SchedulesFragment.newInstance(TYPE.PAST)
                                .apply {
                                    fragmentMap.put("prev", this)
                                    SchedulesPresenter(Injection.provideSportsRepository(ctx), this)
                                }
                    }
                    MENU_NEXT -> {
                        selectedFragment = fragmentMap.get("next") ?: SchedulesFragment.newInstance(TYPE.NEXT)
                                .apply {
                                    fragmentMap.put("next", this)
                                    SchedulesPresenter(Injection.provideSportsRepository(ctx), this)
                                }
                    }
                    MENU_FAV -> {
                        selectedFragment = fragmentMap.get("fav") ?: SchedulesFragment.newInstance(TYPE.FAV)
                                .apply {
                                    fragmentMap.put("fav", this)
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
            val contentFrame = 1
            val navigationView = 2
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

