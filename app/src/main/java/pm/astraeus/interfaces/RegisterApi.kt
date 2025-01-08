package pm.astraeus.interfaces

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

// Data classes for request and response
data class RegisterRequest(val utilizador: String, val email: String, val password: String)
data class RegisterResponse(val message: String)

interface RegisterApi {

    @Headers("Content-Type: application/json")
    @POST("utilizador/create.php") // Endpoint
    fun login(@Body request: RegisterRequest): Call<RegisterResponse>
}