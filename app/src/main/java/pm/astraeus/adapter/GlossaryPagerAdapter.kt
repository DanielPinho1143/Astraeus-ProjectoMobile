package pm.astraeus.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import pm.astraeus.EstadoMissaoFragment
import pm.astraeus.TipoCorpoFragment
import pm.astraeus.TipoMissaoFragment

class GlossaryPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    private val fragments = listOf(
        TipoMissaoFragment(),
        TipoCorpoFragment(),
        EstadoMissaoFragment()
    )

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]
}