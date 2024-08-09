package com.gamstrain.oppeningapp.viewPagerAdp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.gamstrain.oppeningapp.FrLinks
import com.gamstrain.oppeningapp.FrLinksR

class AdapterVp(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FrLinks()
            1 -> FrLinksR()
            else -> throw IndexOutOfBoundsException("Invalid fragment index")
        }
    }
}