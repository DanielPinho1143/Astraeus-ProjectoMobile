package pm.astraeus

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL = "https://esan-tesp-ds-paw.web.ua.pt/tesp-ds-g23/projecto/web/api/"

    // Create and configure Retrofit
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()) // Add Gson for JSON conversion
        .build()

    // Function to create an API service
    fun <T> createService(apiClass: Class<T>): T {
        return retrofit.create(apiClass)
    }
}
