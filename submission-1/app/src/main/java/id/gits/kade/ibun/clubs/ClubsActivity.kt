package id.gits.kade.ibun.clubs

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import id.gits.kade.ibun.Club
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.setContentView

class ClubsActivity : AppCompatActivity() {
    val list: ArrayList<Club> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ClubsActivityUI().setContentView(this)

        list.add(Club("Barcelona FC", "Futbol Club Barcelona, known simply as Barcelona and colloquially as Barça, is a professional football club based in Barcelona, Catalonia, Spain", "https://i.imgur.com/gu4r5Bj.png"))
        list.add(Club("Real Madrid FC", "Real Madrid Club de Fútbol, commonly known as Real Madrid, or simply as Real, is a professional football club based in Madrid, Spain.", "https://i.pinimg.com/originals/9c/bb/92/9cbb92959f72f74684529e15b403dd77.jpg"))
        list.add(Club("Bayern Munich FC", "Fußball-Club Bayern München e.V., commonly known as FC Bayern München, FCB, Bayern Munich, or FC Bayern, is a German sports club based in Munich, Bavaria.", "http://aux.iconspalace.com/uploads/bayern-munchen-logo-icon-256.png"))
        list.add(Club("Manchester City FC", "Manchester City Football Club, also known simply as City or the Cityzens, is a football club based in east Manchester, England. Founded in 1880 as St. Mark's, it became Ardwick Association Football Club in 1887 and Manchester City in 1894.", "http://aux2.iconspalace.com/uploads/manchester-city-logo-icon-256.png"))
        list.add(Club("Manchester United FC", "Manchester United Football Club, commonly known as Man. United or simply United, is a professional football club based in Old Trafford, Greater Manchester, England, that competes in the Premier League, the top flight of English football.", "http://aux2.iconspalace.com/uploads/manchester-united-logo-icon-256.png"))
        list.add(Club("Liverpool FC", "Liverpool Football Club is a professional football club in Liverpool, England, that competes in the Premier League, the top tier of English football.", "http://aux.iconspalace.com/uploads/liverpool-fc-logo-icon-256.png"))
        list.add(Club("AC Milan FC", "Associazione Calcio Milan, commonly referred to as A.C. Milan or simply Milan, is a professional football club in Milan, Italy, founded in 1899.", "http://aux.iconspalace.com/uploads/ac-milan-logo-icon-256.png"))
        list.add(Club("Inter Milan FC", "Football Club Internazionale Milano S.p.A., commonly referred to as Internazionale or simply Inter and colloquially known as Inter Milan outside Italy, is a professional Italian football club based in Milan, Italy.", "http://aux.iconspalace.com/uploads/1171158818.png"))
        list.add(Club("Chelsea FC", "Chelsea Football Club is a professional football club in London, England, that competes in the Premier League. Founded in 1905, the club's home ground since then has been Stamford Bridge.", "https://i.pinimg.com/originals/08/f3/51/08f351de5578d2ce9bddb12fccbe04d1.png"))
        list.add(Club("Arsenal FC", "Arsenal Football Club is a professional football club based in Islington, London, England, that plays in the Premier League, the top flight of English football.", "https://i1.wp.com/dlscenter.com/wp-content/uploads/2017/06/Arsenal-Logo.png?resize=256%2C256"))
        list.add(Club("Fiorentina FC", "ACF Fiorentina S.p.A., commonly referred to as simply Fiorentina, is a professional Italian football club from Florence, Tuscany.", "http://aux.iconspalace.com/uploads/fiorentina-logo-icon-256.png"))
        list.add(Club("AS Roma FC", "Associazione Sportiva Roma, commonly referred to as simply Roma, is a professional Italian football club based in Rome.", "http://de1.iconarchive.com/download/i12784/giannis-zographos/italian-football-club/AS-Roma.ico"))
        list.add(Club("Persib FC", "Persib Bandung adalah sebuah tim sepak bola Indonesia terbesar yang berdiri pada 14 Maret 1933, klub ini berbasis di Bandung, Jawa Barat. Persib saat ini bermain di Gojek Liga 1 Indonesia dan Piala Indonesia.", "https://pbs.twimg.com/profile_images/624141535739711488/1hL2Ca3r_400x400.jpg"))

        val rv = findViewById<RecyclerView>(ClubsActivityUI.recyclerViewId)
        rv.adapter = ClubsAdapter(this, list)
    }

    class ClubsActivityUI : AnkoComponent<ClubsActivity> {
        companion object {
            val recyclerViewId = 1
        }

        override fun createView(ui: AnkoContext<ClubsActivity>) = with(ui) {
            recyclerView {
                id = recyclerViewId
                layoutManager = LinearLayoutManager(context)
            }
        }
    }
}
