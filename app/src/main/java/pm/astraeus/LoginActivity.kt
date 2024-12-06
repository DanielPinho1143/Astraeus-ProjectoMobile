package pm.astraeus

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import pm.astraeus.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun doLogin(view : View) {
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url = "https://esan-tesp-ds-paw.web.ua.pt/tesp-ds-g23/aulas/08/api/users/login.php"

        // Request a string response from the provided URL.
        val postRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener<String>
            { response ->
                var msg = response
                if (response == "OK") {
                    // save login...
                    if (binding.checkGuardar.isChecked) {
                        // guardar em SharedPreference o login
                        getSharedPreferences("pmLogin", Context.MODE_PRIVATE)
                            .edit()
                            .putBoolean("login", true)
                            .putString("user", binding.txtEmail.text.toString())
                            .apply()
                        msg += " + SAVE"
                    }
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }

                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
            },
            Response.ErrorListener {
                Toast.makeText(this, "Erro", Toast.LENGTH_SHORT).show()
            }
        ) {
            override fun getParams(): MutableMap<String, String>? {
                val params: MutableMap<String,String> = HashMap()
                params["email"] = binding.txtEmail.text.toString()
                params["password"] = binding.txtPassword.text.toString()
                return params
            }
        }

        // Add the request to the RequestQueue.
        queue.add(postRequest)
    }

}

