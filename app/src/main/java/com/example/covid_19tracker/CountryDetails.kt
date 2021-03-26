package com.example.covid_19tracker

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.covid_19tracker.databinding.ActivityCountryDetailsBinding


class CountryDetails : AppCompatActivity() {


    private lateinit var binding: ActivityCountryDetailsBinding
    private var positionCountry: Int = 0
    private lateinit var tvCountry: TextView
    private lateinit var tvCases:TextView
    private lateinit var tvRecovered:TextView
    private lateinit var tvCritical:TextView
    private lateinit var tvActive:TextView
    private lateinit var tvTodayCases:TextView
    private lateinit var tvTotalDeaths:TextView
    private lateinit var tvTodayDeaths:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country_details)
        val model = intent.getParcelableExtra<CountryModel>("model")
//        positionCountry = intent.getIntExtra("position",0);

        tvCountry = findViewById(R.id.tvCountry)
        tvCases = findViewById(R.id.tvCases)
        tvRecovered = findViewById(R.id.tvRecovered)
        tvCritical = findViewById(R.id.tvCritical)
        tvActive = findViewById(R.id.tvActive)
        tvTodayCases = findViewById(R.id.tvTodayCases)
        tvTotalDeaths = findViewById(R.id.tvDeaths)
        tvTodayDeaths = findViewById(R.id.tvTodayDeaths)


        tvCountry.text = model?.country
        tvCases.text = model?.cases
        tvRecovered.text = model?.recovered
        tvCritical.text = model?.critical
        tvActive.text = model?.active
        tvTodayCases.text = model?.todayCases
        tvTotalDeaths.text = model?.deaths
        tvTodayDeaths.text = model?.todayDeaths

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == android.R.id.home)
            finish()

        return super.onOptionsItemSelected(item)
    }
}