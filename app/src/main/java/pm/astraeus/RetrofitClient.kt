package pm.astraeus

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL = "https://esan-tesp-ds-paw.web.ua.pt/tesp-ds-g23/projecto/web/api/"

    // Criação e configuração de Retrofit
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()) // Gson para conversão JSON
        .build()

    // Função para criar um serviço de API
    fun <T> createService(apiClass: Class<T>): T {
        return retrofit.create(apiClass)
    }
}