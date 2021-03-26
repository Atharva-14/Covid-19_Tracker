package com.example.covid_19tracker

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.covid_19tracker.databinding.ActivityMainBinding
import org.eazegraph.lib.models.PieModel
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fetchData()

        binding.refreshLayout.setOnRefreshListener {
            fetchData()
            binding.refreshLayout.isRefreshing = false
        }

        binding.btnTrack.setOnClickListener {
            startActivity(Intent(this, AffectedCountries::class.java))
        }
    }

    private fun fetchData() {
        val queue = Volley.newRequestQueue(this)
        val url = "https://disease.sh/v3/covid-19/all"

        binding.loader.start()

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                val jsonObject = JSONObject(response.toString())

                binding.tvCases.text = jsonObject.getString("cases")
                binding.tvRecovered.text = jsonObject.getString("recovered")
                binding.tvCritical.text = jsonObject.getString("critical")
                binding.tvActive.text = jsonObject.getString("active")
                binding.tvTodayCases.text = jsonObject.getString("todayCases")
                binding.tvTodayDeaths.text = jsonObject.getString("todayDeaths")
                binding.tvTotalDeaths.text = jsonObject.getString("deaths")
                binding.tvAffectedCountries.text = jsonObject.getString("affectedCountries")

                binding.piechart.addPieSlice(
                    PieModel(
                        "Cases",
                        Integer.parseInt(binding.tvCases.text.toString()).toFloat(),
                        Color.parseColor("#FFA726")
                    )
                )

                binding.piechart.addPieSlice(
                    PieModel(
                        "Recovered",
                        Integer.parseInt(binding.tvRecovered.text.toString()).toFloat(),
                        Color.parseColor("#66BB6A")
                    )
                )

                binding.piechart.addPieSlice(
                    PieModel(
                        "Deaths",
                        Integer.parseInt(binding.tvTotalDeaths.text.toString()).toFloat(),
                        Color.parseColor("#EF5350")
                    )
                )

                binding.piechart.addPieSlice(
                    PieModel(
                        "Active",
                        Integer.parseInt(binding.tvActive.text.toString()).toFloat(),
                        Color.parseColor("#29B6F6")
                    )
                )

                binding.piechart.startAnimation()
                binding.loader.stop()
                binding.loader.visibility = GONE
                binding.scrollStatus.visibility = VISIBLE

            },
            {
                binding.loader.stop()
                binding.loader.visibility = GONE
                binding.scrollStatus.visibility = VISIBLE
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            })

        // Add the request to the RequestQueue.
        queue.add(stringRequest)

    }
}