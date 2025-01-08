package pm.astraeus.interfaces

import pm.astraeus.model.MissaoRequestData
import pm.astraeus.model.MissaoResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

// Define your API interface for the secondary endpoint
interface MissaoApi {

    // Define a POST request with a request body
    @POST("missao")  // This is the endpoint for the secondary API
    fun getMissaoData(
        @Body requestData: MissaoRequestData  // Pass the data class as the request body
    ): Call<MissaoResponse>  // The response is mapped to MissaoResponse
}
