package rs.raf.rma.nutritiontrackerrma.modules

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import io.reactivex.schedulers.Schedulers.single
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.BuildConfig
import org.koin.dsl.module
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.database.MealsDatabase
import java.util.*
import java.util.concurrent.TimeUnit

val coreModule = module {

    single<SharedPreferences> {
        androidApplication().getSharedPreferences(androidApplication().packageName, Context.MODE_PRIVATE)
    }

    single {
        Room.databaseBuilder(androidContext(), MealsDatabase::class.java, "MealsDB")
            .fallbackToDestructiveMigration()
            .build()
    }

     single(qualifier = named("mealsRetrofit")) { createRetrofit("https://www.themealdb.com/api/json/v1/1/",moshi = get(), httpClient = get()) }
     single(qualifier = named("caloriesRetrofit")) { createRetrofitApiKey("https://api.api-ninjas.com/v1/", moshi = get(), httpClient = get(),"ip3HcRW2RM1+8M6tgjLbpQ==fH3Fe33FzSk0LemQ") }
     //single { createRetrofit("https://api.api-ninjas.com/v1/",moshi = get(), httpClient = get()) }

     single { createMoshi() }

     single { createOkHttpClient() }
}

fun createMoshi(): Moshi {
 return Moshi.Builder()
     .add(Date::class.java, Rfc3339DateJsonAdapter())
     .build()
}
//"https://www.themealdb.com/api/json/v1/1/"
fun createRetrofit(baseUrl:String,moshi: Moshi,
                httpClient: OkHttpClient
): Retrofit {
 return Retrofit.Builder()
     .baseUrl(baseUrl)
     .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
     .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
     .client(httpClient)
     .build()
}
fun createRetrofitApiKey(baseUrl:String,moshi: Moshi,
                   httpClient: OkHttpClient,apiKey: String
): Retrofit {
    val headersInterceptor = Interceptor { chain ->
        val request = chain.request().newBuilder()
            .header("X-Api-Key", apiKey)
            .build()
        chain.proceed(request)
    }

    val okHttpClient = httpClient.newBuilder()
        .addInterceptor(headersInterceptor)
        .build()

    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
        .client(okHttpClient)
        .build()
}
fun createOkHttpClient(): OkHttpClient {
 val httpClient = OkHttpClient.Builder()
 httpClient.readTimeout(60, TimeUnit.SECONDS)
 httpClient.connectTimeout(60, TimeUnit.SECONDS)
 httpClient.writeTimeout(60, TimeUnit.SECONDS)

 if (BuildConfig.DEBUG) {
     val logging = HttpLoggingInterceptor()
     logging.level = HttpLoggingInterceptor.Level.BODY
     httpClient.addInterceptor(logging)
 }

 return httpClient.build()
}

// Metoda koja kreira servis
inline fun <reified T> create(retrofit: Retrofit): T  {
 return retrofit.create(T::class.java)
}




