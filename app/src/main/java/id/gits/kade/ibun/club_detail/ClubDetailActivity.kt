package id.gits.kade.ibun.club_detail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import id.gits.kade.ibun.Club
import id.gits.kade.ibun.R
import org.jetbrains.anko.*

class ClubDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ClubDetailActivityUI().setContentView(this)

        val ivLogo = findViewById<ImageView>(ClubDetailActivityUI.logoId)
        val tvName = findViewById<TextView>(ClubDetailActivityUI.nameId)
        val tvDesc = findViewById<TextView>(ClubDetailActivityUI.descriptionId)

        val club = intent.getParcelableExtra<Club>("club")
        tvName.text = club.name
        tvDesc.text = club.description

        if (club.logo.isNotEmpty())
            Picasso.get()
                    .load(club.logo)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.color.colorError)
                    .into(ivLogo)
    }

    class ClubDetailActivityUI : AnkoComponent<ClubDetailActivity> {
        companion object {
            val logoId = 1
            val nameId = 2
            val descriptionId = 3
        }

        override fun createView(ui: AnkoContext<ClubDetailActivity>) = with(ui) {
            verticalLayout {
                lparams(matchParent, matchParent)
                padding = dip(16)

                gravity = Gravity.CENTER_HORIZONTAL

                imageView {
                    id = logoId
                }.lparams(dip(64), dip(64))

                textView {
                    id = nameId
                    textSize = 20f
                }.lparams {
                    topMargin = dip(16)
                }

                textView {
                    id = descriptionId
                }.lparams {
                    topMargin = dip(16)
                }
            }
        }

    }
}
