package com.novapay.sdk.domain.interceptors

import android.util.Log
import com.google.gson.Gson
import com.novapay.sdk.BuildConfig
import com.novapay.sdk.domain.models.responses.RefreshTokenResult
import com.novapay.sdk.infrastructure.di.Preferences
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.*


class AuthInterceptor constructor(private val sharedPrefs: Preferences): Interceptor {

    init{

    }
    private fun callApi(originalRequest: Request) : Response {

        val client = OkHttpClient
            .Builder()
            .addInterceptor(RequestInterceptor())
            .build();

        //val body = jsonData.toRequestBody("application/x-www-form-urlencoded".toMediaTypeOrNull());
        val formBody: RequestBody = FormBody.Builder()
            .add("client_id",BuildConfig.ClientId )
            .add("client_secret", BuildConfig.ClientSecret)
            .add("grant_type",BuildConfig.GrantType)
            .build()
        val request =  originalRequest.newBuilder()
            .url("${BuildConfig.Protocol}${BuildConfig.NovaUrl}/services/identity/token")
            //.addHeader("Authorization", "Bearer $token")
            .post(formBody)
            .build()
        val call = client.newCall(request)
        return call.execute()

    }
    private suspend fun refreshToken(chain: Interceptor.Chain,
                                     initialRequest: Request): Response {

            try {

                val gson = Gson()
                var response = callApi(initialRequest)
                // make an API call to get new token
                if (response != null) {
                    val resString = response.body?.string()
                    response.close()
                    Log.d("AuthInterceptor", resString.toString())

                    var refreshResponse = gson.fromJson(resString, RefreshTokenResult::class.java)
                    Log.d("AuthInterceptorResponse", refreshResponse.toString())
                    if (!refreshResponse.access_token.isNullOrEmpty()) {
                        sharedPrefs.setAuthToken(refreshResponse.access_token)
                        Log.d("AuthInterceptorToken", refreshResponse.access_token.toString())
                        val newRequest = initialRequest
                            .newBuilder()
                            .header("Authorization", "Bearer ${refreshResponse.access_token}")
                            .build()
                        return chain.proceed(newRequest)
                    }
                }

            }
            catch(e : Exception){
                Log.d("AuthInterceptor", e.message.toString())
            }


        return chain.proceed(initialRequest)
    }
    /*private fun addApiKeyInterceptor(okHttpClientBuilder: OkHttpClient.Builder) {
        okHttpClientBuilder.addInterceptor { chain ->
            val newRequest = chain
                .request()
                .newBuilder()
                .addHeader(X_API_KEY, "Bearer ${apiKeyProvider.apiKey}").build()
            chain.proceed(newRequest)
        }
    }*/
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking {
            sharedPrefs.accessToken.firstOrNull()
        };

        val initialRequest = chain.request()
        //if (!token.isNullOrEmpty()) {
            val authRequest = initialRequest
                .newBuilder()
                .header("Authorization", "Bearer $token")
                .build()
            val response = chain.proceed(authRequest)
            if (response.code == 401) {
                return runBlocking { // this: CoroutineScope
                    response.close()
                    return@runBlocking refreshToken(chain, authRequest)

                }

            } else {
                return response
            }
        //}
        //return chain.proceed(initialRequest)
    }
}