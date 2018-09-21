package id.gits.kade.ibun.match

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import id.gits.kade.ibun.Injection
import id.gits.kade.ibun.data.Match
import id.gits.kade.ibun.utils.replaceFragmentInActivity
import org.jetbrains.anko.*

class MatchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MatchActivityUI().setContentView(this)

        intent.getParcelableExtra<Match>(EXTRA_MATCH).apply {
            val fragment = MatchFragment.newInstance(this).apply {
                replaceFragmentInActivity(this, MatchActivityUI.contentFrameId)
            }
            MatchPresenter(this, Injection.provideSportsRepository(ctx), fragment)
        }
    }

    companion object {
        const val EXTRA_MATCH = "MATCH"
    }

    class MatchActivityUI : AnkoComponent<MatchActivity> {
        companion object {
            const val contentFrameId = 1
        }

        override fun createView(ui: AnkoContext<MatchActivity>) = with(ui) {
            frameLayout {
                id = contentFrameId
            }
        }

    }
}
