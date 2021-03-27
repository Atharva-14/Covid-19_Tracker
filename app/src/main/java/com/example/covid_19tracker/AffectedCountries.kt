package com.example.covid_19tracker

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.covid_19tracker.databinding.ActivityAffectedCountriesBinding
import org.json.JSONArray


class AffectedCountries : AppCompatActivity() {

    private lateinit var binding: ActivityAffectedCountriesBinding

    lateinit var countryModelsList: ArrayList<CountryModel>
    lateinit var countryModel: CountryModel
    lateinit var myCustomAdapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAffectedCountriesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        countryModelsList = ArrayList()

        supportActionBar?.title = "Affected Countries"
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        myCustomAdapter = MyAdapter(applicationContext, countryModelsList)
        binding.listView.adapter = myCustomAdapter
        fetchData()

        binding.listView.onItemClickListener = OnItemClickListener { parent, view, position, id ->
            Log.i("error", position.toString())
            val intent = Intent(this, CountryDetails::class.java)
            val model = myCustomAdapter.countryModelsList[position]
            intent.putExtra("model", model)
            startActivity(intent)
        }

//        binding.listView.adapter = myCustomAdapter

        binding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                myCustomAdapter.filter.filter(s)
                myCustomAdapter.notifyDataSetChanged()
            }

            override fun afterTextChanged(s: Editable) {}
        })

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == android.R.id.home)
            finish()

        return super.onOptionsItemSelected(item)
    }

    private fun fetchData() {
        val queue = Volley.newRequestQueue(this)
        val url = "https://disease.sh/v3/covid-19/countries"

// Request a string response from the provided URL.
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                // Display the first 500 characters of the response string.
                val jsonArray = JSONArray(response)

                for (i in 0 until jsonArray.length()) {

                    val jsonObject = jsonArray.getJSONObject(i)

                    val countryName = jsonObject.getString("country")
                    val cases = jsonObject.getString("cases")
                    val todayCases = jsonObject.getString("todayCases")
                    val deaths = jsonObject.getString("deaths")
                    val todayDeaths = jsonObject.getString("todayDeaths")
                    val recovered = jsonObject.getString("recovered")
                    val active = jsonObject.getString("active")
                    val critical = jsonObject.getString("critical")

                    val `object` = jsonObject.getJSONObject("countryInfo")
                    val flagUrl = `object`.getString("flag")
                    countryModel = CountryModel(
                        flagUrl,
                        countryName,
                        cases,
                        todayCases,
                        deaths,
                        todayDeaths,
                        recovered,
                        active,
                        critical
                    )
                    countryModelsList.add(countryModel)
                }
                myCustomAdapter.notifyDataSetChanged()
                binding.loader.stop()
                binding.loader.visibility = View.GONE

            },
            {
                binding.loader.stop()
                binding.loader.visibility = View.GONE
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            })

// Add the request to the RequestQueue.
        queue.add(stringRequest)
    }
}