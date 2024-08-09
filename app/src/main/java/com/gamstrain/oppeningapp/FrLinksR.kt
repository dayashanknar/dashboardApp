package com.gamstrain.oppeningapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.gamstrain.oppeningapp.Constants.Constants.showToast
import com.gamstrain.oppeningapp.adapters.HoriAdapter
import com.gamstrain.oppeningapp.adapters.LinkListAdap
import com.gamstrain.oppeningapp.databinding.FrLinksBinding
import com.gamstrain.oppeningapp.models.HoriModel
import com.gamstrain.oppeningapp.models.LinkD
import com.gamstrain.oppeningapp.models.RecentLink
import com.gamstrain.oppeningapp.models.TopLink
import com.gamstrain.oppeningapp.repoAndVm.AppDataVm
import com.gamstrain.oppeningapp.repoAndVm.DashVm
import com.gamstrain.oppeningapp.utils.NetworkState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FrLinksR : Fragment(R.layout.fr_links) {
   private lateinit var binding: FrLinksBinding

   private val appDataVm: AppDataVm by viewModels()
   private val dashVm: DashVm by viewModels()
   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding = FrLinksBinding.bind(view)

      val listV = binding.list
      listV.layoutManager = LinearLayoutManager(requireContext())

      lifecycleScope.launch {
         val aT = appDataVm.getAuthToken().first()!!
         dashVm.getDash(aT)

         dashVm.getDashState.observe(viewLifecycleOwner) { state ->
            when (state) {
               is NetworkState.Loading -> {
                  showToast(requireContext(), "Loading...")
               }

               is NetworkState.Success -> {
                  val response = state.data.data.recent_links
                  val list = response.map { mapTopLinkToLinkD(it) }
                  listV.adapter = LinkListAdap(list)
               }

               is NetworkState.Error -> {
                  showToast(requireContext() ,"Error")
               }
            }
         }
      }

   }
   private fun mapTopLinkToLinkD(topLink: RecentLink): LinkD {
      return LinkD(
         thumb = topLink.original_image,
         title = topLink.app,
         des = topLink.title,
         clicks = topLink.total_clicks.toString(),
         link = topLink.web_link,
      )
   }

}