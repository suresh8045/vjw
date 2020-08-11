package com.viswakarma.jewelleryworks.di


import com.viswakarma.jewelleryworks.api.ViswakarmaApiService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class RetrofitModule {

    @Singleton
    @Provides
    fun provideViswakarmaApiService(okHttpClient: OkHttpClient): ViswakarmaApiService {
        return Retrofit.Builder()
//            .baseUrl("https://api.github.com/")
            .baseUrl("https://example.com/")
            .addConverterFactory(GsonConverterFactory.create())
//            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .client(okHttpClient)
            .build()
            .create(ViswakarmaApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()

            .addInterceptor(httpLoggingInterceptor)


         /*   // establish connection to server
            .connectTimeout(CONNECTION_TIMEOUT.toLong(), TimeUnit.SECONDS)
            // time between each byte read from the server
            .readTimeout(READ_TIMEOUT.toLong(), TimeUnit.SECONDS)

            // time between each byte sent to server
            .writeTimeout(WRITE_TIMEOUT.toLong(), TimeUnit.SECONDS)*/

//            .retryOnConnectionFailure(true)

            .build()
    }

    @Singleton
    @Provides
    fun provideloggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }


}