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

            setOnNavigationItemSelectedListener {
                val selectedFragment: SchedulesFragment?
                when (it.itemId) {
                    MENU_PREV -> {
                        selectedFragment = fragmentMap.get("prev") ?: SchedulesFragment.newInstance(true)
                                .apply {
                                    fragmentMap.put("prev", this)
                                    SchedulesPresenter(Injection.provideSportsRepository(), this)
                                }
                    }
                    MENU_NEXT -> {
                        selectedFragment = fragmentMap.get("next") ?: SchedulesFragment.newInstance(false)
                                .apply {
                                    fragmentMap.put("next", this)
                                    SchedulesPresenter(Injection.provideSportsRepository(), this)
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
            SchedulesFragment.newInstance(true).apply {
                replaceFragmentInActivity(this, ScheduleActivityUI.contentFrame)
                SchedulesPresenter(Injection.provideSportsRepository(), this)
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
}

