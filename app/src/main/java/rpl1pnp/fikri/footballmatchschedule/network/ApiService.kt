package rpl1pnp.fikri.footballmatchschedule.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiService {

    fun getInterceptor(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
        okHttpClient.connectTimeout(1, TimeUnit.MINUTES)
        okHttpClient.writeTimeout(5, TimeUnit.MINUTES)
        okHttpClient.readTimeout(5, TimeUnit.MINUTES)
        return okHttpClient.build()
    }

    private fun ApiService(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .client(getInterceptor())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getApiService(): ApiInterface {
        return ApiService().create(ApiInterface::class.java)
    }
}