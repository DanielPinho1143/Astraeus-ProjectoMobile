package pm.astraeus

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import pm.astraeus.databinding.ActivityLoginBinding
import pm.astraeus.interfaces.LoginRequest
import pm.astraeus.interfaces.LoginResponse
import pm.astraeus.interfaces.UserApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    fun doLogin(view: View) {
        val email = binding.txtEmail.text.toString()
        val password = binding.txtPassword.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter email and password.", Toast.LENGTH_SHORT).show()
            return
        }

        // Create the API service
        val userApi = RetrofitClient.createService(UserApi::class.java)

        // Create the login request data
        val loginRequest = LoginRequest(email, password)

        // Make the login request using Retrofit
        userApi.login(loginRequest).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val loginResponse = response.body()!!

                    if (!loginResponse.jwt.isNullOrEmpty()) {
                        // Save JWT or proceed with other operations
                        Toast.makeText(this@LoginActivity, "Login successful!", Toast.LENGTH_SHORT).show()

                        // Optionally, save the user and JWT to SharedPreferences
                        if (binding.checkGuardar.isChecked) {
                            getSharedPreferences("pmLogin", Context.MODE_PRIVATE)
                                .edit()
                                .putBoolean("login", true)
                                .putString("user", email)
                                .putString("jwt", loginResponse.jwt)
                                .apply()
                        }

                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this@LoginActivity, "Login failed: ${loginResponse.message}", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@LoginActivity, "Login failed: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                // Handle failure (e.g., no network)
                Toast.makeText(this@LoginActivity, "Error: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
