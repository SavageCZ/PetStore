package cz.mendelu.pef.compose.petstore.di

import com.google.gson.GsonBuilder

import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val retrofitModule = module {
    factory { provideInterceptor() }
    factory { provideOkHttpClient(get()) }
    single { provideRetrofit(get()) }
}
// logování (debbugování a vypisování je sice kezký, ale log je log

fun provideInterceptor(): HttpLoggingInterceptor {
    val logging = HttpLoggingInterceptor()
    logging.level = HttpLoggingInterceptor.Level.BODY
    return logging
}

// inicializuji klienta, který bude použit pro komunikaci

fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
    val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
    val dispatcher = Dispatcher()
    httpClient.dispatcher(dispatcher)
    return httpClient.addInterceptor(httpLoggingInterceptor).build()
}

// Pomocí dependency injection nám to postupně probublává do jednotlivých míst
fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {

    // Z JSON objektů jsou objekty rázem "kotliňácký"
    val gson = GsonBuilder()
        .setLenient()
        .create()

    // Objekt Rektrofitu               Vzorové API
    return Retrofit.Builder().baseUrl("https://petstore.swagger.io/v2/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
}
