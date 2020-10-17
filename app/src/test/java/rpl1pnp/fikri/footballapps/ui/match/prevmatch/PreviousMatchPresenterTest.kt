package rpl1pnp.fikri.footballapps.ui.match.prevmatch

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
import rpl1pnp.fikri.footballapps.model.SearchResponse
import rpl1pnp.fikri.footballapps.network.ApiRepository
import rpl1pnp.fikri.footballapps.ui.TestContextProvider
import rpl1pnp.fikri.footballapps.view.PreviousMatchView

class PreviousMatchPresenterTest {
    @Mock
    private lateinit var view: PreviousMatchView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var apiResponse: Deferred<String>

    private lateinit var presenter: PreviousMatchPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = PreviousMatchPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getListMatch() {
        val events: MutableList<Events> = mutableListOf()
        val response = EventsResponse(events)
        val idLeague = "4328"

        runBlocking {
            Mockito.`when`(apiRepository.doRequestAsync(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)

            Mockito.`when`(apiResponse.await()).thenReturn("")

            Mockito.`when`(gson.fromJson("", EventsResponse::class.java))
                .thenReturn(response)

            presenter.getListMatch(idLeague)

            Mockito.verify(view).showLoading()
            Mockito.verify(view).showListMatch(events)
            Mockito.verify(view).hideLoading()
        }
    }

    @Test
    fun getSearchMatch() {
        val events: MutableList<Events> = mutableListOf()
        val response = SearchResponse(events)
        val query = "Liverpool"

        runBlocking {
            Mockito.`when`(apiRepository.doRequestAsync(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)

            Mockito.`when`(apiResponse.await()).thenReturn("")

            Mockito.`when`(gson.fromJson("", SearchResponse::class.java))
                .thenReturn(response)

            presenter.getSearchMatch(query)

            Mockito.verify(view).showLoading()
            Mockito.verify(view).searchMatch(events)
            Mockito.verify(view).hideLoading()
        }
    }
}