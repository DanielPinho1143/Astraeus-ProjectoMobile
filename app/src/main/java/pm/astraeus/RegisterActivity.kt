package pm.astraeus

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import pm.astraeus.databinding.ActivityRegisterBinding
import pm.astraeus.interfaces.LoginRequest
import pm.astraeus.interfaces.LoginResponse
import pm.astraeus.interfaces.RegisterApi
import pm.astraeus.interfaces.RegisterRequest
import pm.astraeus.interfaces.RegisterResponse
import pm.astraeus.interfaces.UserApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityRegisterBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    fun doRegister(view: View) {
        val utilizador = binding.txtRegisterUser.text.toString()
        val email = binding.txtRegisterEmail.text.toString()
        val password = binding.txtResgisterPassword.text.toString()

        if (utilizador.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Todos os campos são obrigatórios.", Toast.LENGTH_LONG).show()
            return
        }

        // Serviço API
        val registerApi = RetrofitClient.createService(RegisterApi::class.java)

        // Atribuição de dados necessários
        val registerRequest = RegisterRequest(utilizador, email, password)

        // Pedido de registo
        registerApi.login(registerRequest).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    Toast.makeText(this@RegisterActivity, "Registo criado!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))

                } else {
                    Toast.makeText(this@RegisterActivity, "Erro ao criar registo: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                // Manuseamento de falhas
                Toast.makeText(this@RegisterActivity, "Erro: ${t.localizedMessage}", Toast.LENGTH_LONG).show()
            }
        })
    }

    fun goToLogin(view: View) {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}