package dev.shiron.smallstride.domain

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

const val API_ORIGIN = "https://pck.shiron.dev"
const val NEW_TARGET_URL = "${API_ORIGIN}/target/"

// Retrofitのインターフェースを定義します
interface ApiInterface {
    @POST("new")
    fun getTargetData(@Body request: ReqTargetClass): Call<TargetClass>
}

private val okHttpClient = OkHttpClient.Builder()
    .connectTimeout(60, TimeUnit.MINUTES) // 接続のタイムアウトを60秒に設定
    .readTimeout(60, TimeUnit.MINUTES) // 読み取りのタイムアウトを60秒に設定
    .writeTimeout(60, TimeUnit.MINUTES) // 書き込みのタイムアウトを60秒に設定
    .build()

// Retrofitクライアントを初期化します
private val retrofit = Retrofit.Builder()
    .baseUrl(NEW_TARGET_URL)
    .addConverterFactory(GsonConverterFactory.create(
        GsonBuilder().setDateFormat("EEE, dd MMM yyyy HH:mm:ss Z").create()
    ))
    .client(okHttpClient)
    .build()

// APIを呼び出す関数を定義します
fun callApi(reqTargetClass: ReqTargetClass, onFailure: (Throwable) -> Unit = {},onResponse: (TargetClass?) -> Unit) {
    val apiInterface = retrofit.create(ApiInterface::class.java)
    val call = apiInterface.getTargetData(reqTargetClass)

    call.enqueue(object : retrofit2.Callback<TargetClass> {
        override fun onResponse(call: Call<TargetClass>, response: retrofit2.Response<TargetClass>) {
            if (response.isSuccessful) {
                onResponse(response.body())
            } else {
                onResponse(null)
            }
        }

        override fun onFailure(call: Call<TargetClass>, t: Throwable) {
            onFailure(t)
        }
    })
}
