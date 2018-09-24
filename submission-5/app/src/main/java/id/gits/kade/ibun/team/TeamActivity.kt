package id.gits.kade.ibun.team

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import id.gits.kade.ibun.Injection
import id.gits.kade.ibun.data.Team
import id.gits.kade.ibun.utils.replaceFragmentInActivity
import org.jetbrains.anko.*

class TeamActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TeamActivityUI().setContentView(this)

        intent.getParcelableExtra<Team>(EXTRA_TEAM).apply {
            val fragment = TeamFragment.newInstance(this).apply {
                replaceFragmentInActivity(this, TeamActivityUI.contentFrameId)
            }
            TeamPresenter(this, Injection.provideSportsRepository(ctx), fragment)
        }

        title = "Team Detail"
    }

    companion object {
        const val EXTRA_TEAM = "TEAM"
    }

    class TeamActivityUI : AnkoComponent<TeamActivity> {
        companion object {
            const val contentFrameId = 1
        }

        override fun createView(ui: AnkoContext<TeamActivity>) = with(ui) {
            frameLayout {
                id = contentFrameId
            }
        }

    }
}
