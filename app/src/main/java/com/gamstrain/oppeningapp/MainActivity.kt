package com.gamstrain.oppeningapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.gamstrain.oppeningapp.Constants.Constants.showToast
import com.gamstrain.oppeningapp.adapters.HoriAdapter
import com.gamstrain.oppeningapp.databinding.ActivityMainBinding
import com.gamstrain.oppeningapp.models.HoriModel
import com.gamstrain.oppeningapp.repoAndVm.AppDataVm
import com.gamstrain.oppeningapp.repoAndVm.DashVm
import com.gamstrain.oppeningapp.utils.NetworkState
import com.gamstrain.oppeningapp.viewPagerAdp.AdapterVp
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val appDataVm: AppDataVm by viewModels()
    private val dashVm: DashVm by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val viewPager = binding.viewPager
        val adapter = AdapterVp(this)
        viewPager.adapter = adapter


        viewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    0 -> {
                        //TODO change color
                    }

                    1 -> {
                        //TODO change bg color
                    }

                }
            }
        })


        val horiRcv = binding.horiRcv
        horiRcv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

// Set up your adapter and other configurations here

        //diff ui above
        lifecycleScope.launch {
            updateAuthToken()
            val aT = appDataVm.getAuthToken().first()!!
            dashVm.getDash(aT)

            dashVm.getDashState.observe(this@MainActivity) { state ->
                when (state) {
                    is NetworkState.Loading -> {
                    }

                    is NetworkState.Success -> {
                        val response = state.data
                        val list = listOf(
                            HoriModel(
                                R.drawable.ic_click,
                                response.today_clicks.toString(),
                                "Today's clicks"
                            ),
                            HoriModel(
                                R.drawable.ic_location,
                                response.top_location,
                                "Top locations"
                            ),
                            HoriModel(R.drawable.ic_src, response.top_source, "Top sources"),
                            HoriModel(
                                R.drawable.ic_click,
                                response.total_clicks.toString(),
                                "Total clicks"
                            ),
                            HoriModel(
                                R.drawable.ic_link,
                                response.total_links.toString(),
                                "Total links"
                            )
                        )
                        horiRcv.adapter = HoriAdapter(list)
                    }

                    is NetworkState.Error -> {
                        showToast(this@MainActivity, "Error")
                    }
                }
            }
        }
    }


    private fun updateAuthToken() {
        val authToken =
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MjU5MjcsImlhdCI6MTY3NDU1MDQ1MH0.dCkW0ox8tbjJA2GgUx2UEwNlbTZ7Rr38PVFJevYcXFI"
        appDataVm.updateAuthToken(authToken)

    }


}