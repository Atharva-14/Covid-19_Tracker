package com.example.covid_19tracker

import android.os.Parcelable
import androidx.versionedparcelable.ParcelField
import kotlinx.android.parcel.Parcelize

@Parcelize
class CountryModel(val flag: String,
                        val country: String,
                        val cases: String,
                        val todayCases: String,
                        val deaths: String,
                        val todayDeaths: String,
                        val recovered: String,
                        val active: String,
                        val critical: String) : Parcelable{

}