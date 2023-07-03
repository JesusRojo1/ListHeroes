package com.jesus_rojo_proyects.superherolist.UI

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.github.mikephil.charting.charts.RadarChart
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.jesus_rojo_proyects.superherolist.MainActivity
import com.jesus_rojo_proyects.superherolist.R
import com.jesus_rojo_proyects.superherolist.databinding.ActivityDetailSuperHeroBinding
import com.jesus_rojo_proyects.superherolist.interfaces.Provider
import com.jesus_rojo_proyects.superherolist.models.SuperHeroDetailModel
import com.jesus_rojo_proyects.superherolist.network.Network
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class DetailSuperHeroActivity : AppCompatActivity() {

    lateinit var radarChart: RadarChart

    companion object {
        const val SUPER_HERO_ID = "idSuperHero"
    }

    private lateinit var binding: ActivityDetailSuperHeroBinding

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailSuperHeroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val idSuperHero = intent.getStringExtra(SUPER_HERO_ID)
        getSuperHeroInformation(idSuperHero.orEmpty())
        radarChart = binding.radarChartView
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun getSuperHeroInformation(idSuperHero: String) {
        if (Network.networkExists(this)) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val superHeroDetail =
                        getRetrofit().create(Provider::class.java).getSuperHeroesId(idSuperHero)
                    if (superHeroDetail.body() != null) {
                        runOnUiThread { createUIDetail(superHeroDetail.body()!!) }
                    }
                } catch (e: IOException) {
                    Log.d("ROJO", "No se establecio al conexion")
                }
            }
        } else {
            if (MainActivity.flag_message_lost_network) {
                Toast.makeText(this, getString(R.string.lost_connection), Toast.LENGTH_LONG).show()
                MainActivity.flag_message_lost_network = false
            }
        }
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl("https://superheroapi.com/api/10225998847571749/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun createUIDetail(superHeroDetail: SuperHeroDetailModel) {
        Picasso.get().load(superHeroDetail.imageUrl.url).into(binding.ImageDetailView)
        binding.titleNameValueViewDetail.text = superHeroDetail.name
        binding.titleGenderValueViewDetail.text = superHeroDetail.appearance.gender
        binding.titleRaceValueViewDetail.text = superHeroDetail.appearance.race
        binding.BiographyValueView.text =
            "${superHeroDetail.biography.fullName} - place birth, ${superHeroDetail.biography.placeOfBirth} - working as ${superHeroDetail.work.occupation} - often frequented ${superHeroDetail.work.base} and its alignment is as an individual ${superHeroDetail.biography.alignment}"

        val list: ArrayList<RadarEntry> = ArrayList()
        val optionsRadar =
            listOf("Intelligence", "Strength", "Speed", "Durability", "Power", "Combat")

        list.add(RadarEntry(superHeroDetail.powerStats.intelligence.toFloatOrNull() ?: 0.0f))
        list.add(RadarEntry(superHeroDetail.powerStats.strength.toFloatOrNull() ?: 0.0f))
        list.add(RadarEntry(superHeroDetail.powerStats.speed.toFloatOrNull() ?: 0.0f))
        list.add(RadarEntry(superHeroDetail.powerStats.durability.toFloatOrNull() ?: 0.0f))
        list.add(RadarEntry(superHeroDetail.powerStats.power.toFloatOrNull() ?: 0.0f))
        list.add(RadarEntry(superHeroDetail.powerStats.combat.toFloatOrNull() ?: 0.0f))

        val radarDataSet = RadarDataSet(list, "")

        radarDataSet.setColors(ColorTemplate.MATERIAL_COLORS, 255)
        radarDataSet.lineWidth = 3f
        radarDataSet.valueTextSize = 10f
        radarDataSet.valueTextColor = Color.RED
        val radarData = RadarData()
        radarData.addDataSet(radarDataSet)
        radarChart.data = radarData
        radarChart.description.text = "Power Stats"
        radarChart.xAxis.valueFormatter = IndexAxisValueFormatter(optionsRadar)
        radarChart.animateY(3000)
    }
}