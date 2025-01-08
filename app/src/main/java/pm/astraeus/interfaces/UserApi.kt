package pm.astraeus.interfaces

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

// Data classes for request and response
data class LoginRequest(val email: String, val password: String)
data class LoginResponse(val message: String, val jwt: String?)

interface UserApi {

    @Headers("Content-Type: application/json")
    @POST("utilizador/login.php") // Endpoint
    fun login(@Body request: LoginRequest): Call<LoginResponse>
}