package com.gamstrain.oppeningapp

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
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
import com.gamstrain.oppeningapp.utils.getGreeting
import com.gamstrain.oppeningapp.viewPagerAdp.AdapterVp
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val appDataVm: AppDataVm by viewModels()
    private val dashVm: DashVm by viewModels()

    private var supportNumber: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.greetingTxt.text = getGreeting()
        //chart
        val lineChart = binding.lineChart
        lineChart.gradientFillColors = intArrayOf(
            Color.parseColor("#D064B5F6"), Color.TRANSPARENT
        )
        lineChart.animation.duration = ANIMATION_DURATION
        lineChart.animate(lineSet)

        //line chart

        binding.whBtn.setOnClickListener {
            supportNumber?.let { openWhatsAppChat(it) }
        }

        val viewPager = binding.viewPager
        val adapter = AdapterVp(this)
        viewPager.adapter = adapter

        binding.topLinkBtn.setOnClickListener {
            viewPager.setCurrentItem(0, true)
        }

        binding.recentLinkBtn.setOnClickListener {
            viewPager.setCurrentItem(1, true)
        }

        binding.topLinkBtn.setBackgroundResource(R.drawable.dr_blue_15)

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    0 -> {

                        binding.topLinkBtn.setBackgroundResource(R.drawable.dr_blue_15)

                        binding.topLinkBtn.setTextColor(
                            ContextCompat.getColor(
                                this@MainActivity, R.color.white
                            )
                        )
                        binding.recentLinkBtn.setBackgroundResource(R.drawable.dr_obg_20)
                        binding.recentLinkBtn.setTextColor(
                            ContextCompat.getColor(
                                this@MainActivity, R.color.black
                            )
                        )
                    }

                    1 -> {
                        binding.recentLinkBtn.setBackgroundResource(R.drawable.dr_blue_15)
                        binding.recentLinkBtn.setTextColor(
                            ContextCompat.getColor(
                                this@MainActivity, R.color.white
                            )
                        )
                        binding.topLinkBtn.setBackgroundResource(R.drawable.dr_obg_20)
                        binding.topLinkBtn.setTextColor(
                            ContextCompat.getColor(
                                this@MainActivity, R.color.black
                            )
                        )
                    }
                }
            }
        })


        val horiRcv = binding.horiRcv
        horiRcv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        //diff ui above

        lifecycleScope.launch {
            updateAuthToken()
            val aT = appDataVm.getAuthToken().first()!!
            dashVm.getDash(aT)

            dashVm.getDashState.observe(this@MainActivity) { state ->
                when (state) {
                    is NetworkState.Loading -> {
                        showToast(this@MainActivity, "Loading...")
                    }

                    is NetworkState.Success -> {
                        val response = state.data
                        supportNumber = response.support_whatsapp_number

                        val list = listOf(
                            HoriModel(
                                R.drawable.ic_click,
                                response.today_clicks.toString(),
                                "Today's clicks"
                            ),
                            HoriModel(
                                R.drawable.ic_location, response.top_location, "Top locations"
                            ),
                            HoriModel(R.drawable.ic_src, response.top_source, "Top sources"),
                            HoriModel(
                                R.drawable.ic_click,
                                response.total_clicks.toString(),
                                "Total clicks"
                            ),
                            HoriModel(
                                R.drawable.ic_link, response.total_links.toString(), "Total links"
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

    companion object {
        private val lineSet = listOf(
            "Jan" to 0f,
            "Feb" to 25f,
            "Mar" to 70f,
            "Apr" to 70f,
            "May" to 100f,
            "Jun" to 50f,
            "Jul" to 25f,
            "Aug" to 100f,
            "Sept" to 25f
        )
        private const val ANIMATION_DURATION = 500L
    }

    private fun openWhatsAppChat(phoneNumber: String) {
        val url = "https://api.whatsapp.com/send?phone=$phoneNumber"
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }
}