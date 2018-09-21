package id.gits.kade.ibun.schedules

import id.gits.kade.ibun.argumentCaptor
import id.gits.kade.ibun.any
import id.gits.kade.ibun.capture
import id.gits.kade.ibun.data.Club
import id.gits.kade.ibun.data.Match
import id.gits.kade.ibun.data.source.SportsDataSource
import id.gits.kade.ibun.data.source.SportsRepository
import id.gits.kade.ibun.match.MatchContract
import id.gits.kade.ibun.match.MatchPresenter
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.*
import org.mockito.Mockito.*

class MatchPresenterTest {
    @Mock
    private lateinit var sportsRepository: SportsRepository
    @Mock
    private lateinit var view: MatchContract.View

    private lateinit var presenter: MatchPresenter

    @Captor
    private lateinit var getHomeClubCallbackCaptor: ArgumentCaptor<SportsDataSource.GetClubCallback>

    private val match = Match("1", "1", "2",
            "Persikasi", "Persib", strDate = "12/12/2012",
            strTime = "14:00:00+00:00", isPast = false)

    private lateinit var clubHome: Club
    private lateinit var clubAway: Club

    @Before
    fun setupPresenter() {
        MockitoAnnotations.initMocks(this)

        presenter = MatchPresenter(match, sportsRepository, view)

        clubHome = Club("1", "Persikasi", "Sample Description", "http://example.com/image.jpg")
        clubAway = Club("2", "Persib", "Sample Description", "http://example.com/image.jpg")
    }

    @Test
    fun createPresenter_setsThePresenterToView() {
        // Get a reference to the class under test
        presenter = MatchPresenter(match, sportsRepository, view)

        // Then the presenter is set to the view
        verify(view).presenter = presenter
    }

    @Test
    fun loadHomeClubFromRepositoryAndLoadIntoView() {
        with(presenter) {
            getClub()
        }

        // Callback is captured and invoked with stubbed tasks
        verify(sportsRepository, times(2)).getClub(any(), capture(getHomeClubCallbackCaptor))
        getHomeClubCallbackCaptor.value.onClubLoaded(clubHome)

        val inOrder = inOrder(view)
        inOrder.verify(view).showLoading()
        inOrder.verify(view).hideLoading()

        val getClubArgumentCaptor = argumentCaptor<Club>()
        verify(view).showClubAway(capture(getClubArgumentCaptor))

        assertTrue(getClubArgumentCaptor.value.idTeam == "1")
    }


    @Test
    fun loadClubFromRepositoryAndShowError() {
        with(presenter) {
            getClub()
        }

        // Callback is captured and invoked with stubbed tasks
        verify(sportsRepository, times(2)).getClub(any(), capture(getHomeClubCallbackCaptor))
        val error = "Unknown error"
        getHomeClubCallbackCaptor.value.onError(error)

        val inOrder = inOrder(view)
        inOrder.verify(view).showLoading()
        inOrder.verify(view).hideLoading()

        val getClubArgumentCaptor = argumentCaptor<String>()
        verify(view).showError(capture(getClubArgumentCaptor))


        assertTrue(getClubArgumentCaptor.value == error)
    }

}