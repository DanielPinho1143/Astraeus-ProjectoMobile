package pm.astraeus

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClientAPI {

    private const val BASE_URL_PHP = "https://esan-tesp-ds-paw.web.ua.pt/tesp-ds-g23/projecto/web/api.php/"

    // Create and configure Retrofit for the secondary API
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL_PHP)
        .addConverterFactory(GsonConverterFactory.create()) // Gson for JSON conversion
        .build()

    // Function to create a service for the secondary API
    fun <T> createService(apiClass: Class<T>): T {
        return retrofit.create(apiClass)
    }
}
