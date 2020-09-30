package rpl1pnp.fikri.footballmatchschedule.ui.detailleague

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import rpl1pnp.fikri.footballmatchschedule.model.LeagueDetailResponse
import rpl1pnp.fikri.footballmatchschedule.network.ApiService

class DetailLeagueViewModel : ViewModel() {

    private val leagueDetail = MutableLiveData<LeagueDetailResponse>()
    val gson = Gson()
    private val apiService = ApiService()
    private val loading = MutableLiveData<Boolean>()

    fun getLeagueList(idLeague: String?) {
        loading.value = true
        apiService.getApiService().lookupLeague(idLeague)
            .enqueue(object : Callback<LeagueDetailResponse> {
                override fun onFailure(call: Call<LeagueDetailResponse>, t: Throwable) {
                    loading.value = false
                    leagueDetail.value = null
                }

                override fun onResponse(
                    call: Call<LeagueDetailResponse>,
                    response: Response<LeagueDetailResponse>
                ) {
                    if (response.isSuccessful) {
                        loading.value = false
                        leagueDetail.value = response.body()
                    } else {
                        loading.value = false
                        leagueDetail.value = null
                    }
                }
            })
    }

    fun observeLeague(): MutableLiveData<LeagueDetailResponse> {
        return leagueDetail
    }

    fun observeLoading(): MutableLiveData<Boolean> {
        return loading
    }
}