package pm.astraeus

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isEmpty
import com.google.android.material.bottomnavigation.BottomNavigationView
import pm.astraeus.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object {
        lateinit var navigation : BottomNavigationView
    }

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        navigation = binding.bottomNavigation

        binding.bottomNavigation.setOnItemSelectedListener {
            binding.placeholder.removeAllViews()
            when(it.itemId) {
                R.id.navExplore -> {
                    supportFragmentManager
                        .beginTransaction()
                        .add(R.id.placeholder, ExploreFragment())
                        .commit()
                }

                R.id.navGlossary -> {
                    supportFragmentManager
                        .beginTransaction()
                        .add(R.id.placeholder, GlossaryFragment())
                        .commit()
                }
            }
            return@setOnItemSelectedListener true
        }
    }

    override fun onStart() {
        super.onStart()

        if (binding.placeholder.isEmpty()) {
            binding.bottomNavigation.selectedItemId = R.id.navExplore
        }
    }
}