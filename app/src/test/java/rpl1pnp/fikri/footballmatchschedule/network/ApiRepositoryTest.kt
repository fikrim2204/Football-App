package rpl1pnp.fikri.footballmatchschedule.network

import org.junit.Test

import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class ApiRepositoryTest {

    @Test
    fun doRequest() {
        val apiRepository = mock(ApiRepository::class.java)
        val url = "https://thesportsdb.com/1/v1/json/lookupleague.php?id=4328"
        apiRepository.doRequest(url)
        verify(apiRepository).doRequest(url)
    }
}