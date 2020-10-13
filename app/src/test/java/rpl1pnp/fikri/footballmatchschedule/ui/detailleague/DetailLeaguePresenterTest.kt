package rpl1pnp.fikri.footballmatchschedule.ui.detailleague

import com.google.gson.Gson
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers

import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import rpl1pnp.fikri.footballmatchschedule.model.LeagueDetail
import rpl1pnp.fikri.footballmatchschedule.model.LeagueDetailResponse
import rpl1pnp.fikri.footballmatchschedule.network.ApiRepository
import rpl1pnp.fikri.footballmatchschedule.ui.TestContextProvider
import rpl1pnp.fikri.footballmatchschedule.view.DetailLeagueView

class DetailLeaguePresenterTest {
    @Mock
    private lateinit var view: DetailLeagueView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var apiResponse: Deferred<String>

    private lateinit var presenter: DetailLeaguePresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = DetailLeaguePresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getLeagueList() {
        val league: MutableList<LeagueDetail> = mutableListOf()
        val response = LeagueDetailResponse(league)
        val idLeague = "4328"

        runBlocking {
            Mockito.`when`(apiRepository.doRequest(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)

            Mockito.`when`(apiResponse.await()).thenReturn("")

            Mockito.`when`(gson.fromJson("", LeagueDetailResponse::class.java))
                .thenReturn(response)

            presenter.getLeagueList(idLeague)

            Mockito.verify(view).showLoading()
            Mockito.verify(view).showLeagueList(league)
            Mockito.verify(view).hideLoading()
        }
    }
}