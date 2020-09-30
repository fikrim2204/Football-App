package rpl1pnp.fikri.footballmatchschedule.ui.match

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Response
import rpl1pnp.fikri.footballmatchschedule.model.EventsResponse
import rpl1pnp.fikri.footballmatchschedule.model.TeamResponse
import rpl1pnp.fikri.footballmatchschedule.network.ApiService

class MatchViewModel : ViewModel() {
    private val detailMatch = MutableLiveData<EventsResponse>()
    private val logoHome = MutableLiveData<TeamResponse>()
    private val logoAway = MutableLiveData<TeamResponse>()
    val gson = Gson()
    private var apiService = ApiService()
    private val loading = MutableLiveData<Boolean>()

    fun getEvent(eventId: String?) {
        loading.value = true
        apiService.getApiService().detailMatch(eventId)
            .enqueue(object : retrofit2.Callback<EventsResponse> {
                override fun onFailure(call: Call<EventsResponse>, t: Throwable) {
                    loading.value = false
                    detailMatch.value = null
                }

                override fun onResponse(
                    call: Call<EventsResponse>,
                    response: Response<EventsResponse>
                ) {
                    if (response.isSuccessful) {
                        loading.value = false
                        detailMatch.value = response.body()
                    } else {
                        loading.value = false
                        detailMatch.value = null
                    }
                }
            })
    }

//    fun getEvent(eventId: String?) {
//        loading.value = true
//        Log.d("MatchViewModel", "progressbar on")
//        doAsync {
//            val data = gson.fromJson(
//                apiRepositori.doRequest(
//                    TheSportDBApi.getDetailMatch(
//                        eventId
//                    )
//                ),
//                EventsResponse::class.java
//            )
//
//            uiThread {
//                loading.value = false
//                Log.d("MatchViewModel", "progressbar off")
//                detailMatch.value = data
//            }
//        }
//    }

    fun getLogoHome(teamId: String?) {
        loading.value = true
        apiService.getApiService().getTeam(teamId)
            .enqueue(object : retrofit2.Callback<TeamResponse> {
                override fun onFailure(call: Call<TeamResponse>, t: Throwable) {
                    loading.value = false
                    logoHome.value = null
                }

                override fun onResponse(
                    call: Call<TeamResponse>,
                    response: Response<TeamResponse>
                ) {
                    if (response.isSuccessful) {
                        loading.value = false
                        logoHome.value = response.body()
                    } else {
                        loading.value = false
                        logoHome.value = null
                    }
                }
            })
    }

//    fun getLogoHome(teamId: String?) {
//        loading.value = true
//        doAsync {
//            val data = gson.fromJson(
//                apiRepositori.doRequest(
//                    TheSportDBApi.getTeams(
//                        teamId
//                    )
//                ),
//                TeamResponse::class.java
//            )
//
//            uiThread {
//                loading.value = false
//                    logoHome.value = data
//            }
//        }
//    }

    fun getLogoAway(teamId: String?) {
        loading.value = true
        apiService.getApiService().getTeam(teamId)
            .enqueue(object : retrofit2.Callback<TeamResponse> {
                override fun onFailure(call: Call<TeamResponse>, t: Throwable) {
                    loading.value = false
                    logoAway.value = null
                }

                override fun onResponse(
                    call: Call<TeamResponse>,
                    response: Response<TeamResponse>
                ) {
                    if (response.isSuccessful) {
                        loading.value = false
                        logoAway.value = response.body()
                    } else {
                        loading.value = false
                        logoAway.value = null
                    }
                }
            })
    }

//    fun getLogoAway(teamId: String?) {
//        loading.value = true
//        doAsync {
//            val data = gson.fromJson(
//                apiRepositori.doRequest(
//                    TheSportDBApi.getTeams(
//                        teamId
//                    )
//                ),
//                TeamResponse::class.java
//            )
//
//            uiThread {
//                loading.value = false
//                logoAway.value = data
//            }
//        }
//    }

    fun observeEvent(): MutableLiveData<EventsResponse> {
        return detailMatch
    }

    fun observeLogoHome(): MutableLiveData<TeamResponse> {
        return logoHome
    }

    fun observeLogoAway(): MutableLiveData<TeamResponse> {
        return logoAway
    }

    fun observeLoading(): MutableLiveData<Boolean> {
        return loading
    }
}