package rpl1pnp.fikri.footballapps.detailmatch

import com.google.gson.Gson
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import rpl1pnp.fikri.footballapps.model.Events
import rpl1pnp.fikri.footballapps.model.EventsResponse
import rpl1pnp.fikri.footballapps.network.ApiRepository
import rpl1pnp.fikri.footballapps.ui.TestContextProvider
import rpl1pnp.fikri.footballapps.ui.detailmatch.DetailMatchPresenter
import rpl1pnp.fikri.footballapps.view.DetailMatchView

class DetailMatchPresenterTest {
    @Mock
    private lateinit var view: DetailMatchView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var apiResponse: Deferred<String>

    private lateinit var presenter: DetailMatchPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter =
            DetailMatchPresenter(
                view,
                apiRepository,
                gson,
                TestContextProvider()
            )
    }

    @Test
    fun getEvent() {
        val events: MutableList<Events> = mutableListOf()
        val response = EventsResponse(events)
        val eventId = "441613"

        runBlocking {
            Mockito.`when`(apiRepository.doRequestAsync(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)

            Mockito.`when`(apiResponse.await()).thenReturn("")

            Mockito.`when`(gson.fromJson("", EventsResponse::class.java))
                .thenReturn(response)

            presenter.getEvent(eventId)

            Mockito.verify(view).showLoading()
            Mockito.verify(view).showDetail(events)
            Mockito.verify(view).hideLoading()
        }
    }

    @Test
    fun getLogo() {
        val teams: MutableList<Team> = mutableListOf()
        val response = TeamResponse(teams)
        val idTeam = "133602"

        runBlocking {
            Mockito.`when`(apiRepository.doRequestAsync(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)

            Mockito.`when`(apiResponse.await()).thenReturn("")

            Mockito.`when`(gson.fromJson("", TeamResponse::class.java))
                .thenReturn(response)

            presenter.getLogo(idTeam, true)

            Mockito.verify(view).showLoading()
            Mockito.verify(view).getLogoTeam(teams, true)
            Mockito.verify(view).hideLoading()
        }

    }
}