package pm.astraeus

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import pm.astraeus.databinding.ActivitySplashBinding
import pm.astraeus.util.BaseActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity() {

    private val binding by lazy {
        ActivitySplashBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({
            val login = getSharedPreferences("pmLogin", Context.MODE_PRIVATE)
                .getBoolean("login",false)
            if (login) {
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
            }
            finish()
        }, 3000)
    }
}