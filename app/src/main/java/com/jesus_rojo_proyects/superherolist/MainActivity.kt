package com.jesus_rojo_proyects.superherolist

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.jesus_rojo_proyects.superherolist.UI.DetailSuperHeroActivity
import com.jesus_rojo_proyects.superherolist.UI.DetailSuperHeroActivity.Companion.SUPER_HERO_ID
import com.jesus_rojo_proyects.superherolist.adapters.SuperHeroAdapter
import com.jesus_rojo_proyects.superherolist.databinding.ActivityMainBinding
import com.jesus_rojo_proyects.superherolist.interfaces.Provider
import com.jesus_rojo_proyects.superherolist.models.Results
import com.jesus_rojo_proyects.superherolist.models.SuperHeroModel
import com.jesus_rojo_proyects.superherolist.network.Network
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class MainActivity : AppCompatActivity() {

    companion object {
        var flag_message_lost_network = true
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var retrofit: Retrofit
    private lateinit var adapter: SuperHeroAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        retrofit = getRetrofit()
        initUI()
    }

    private fun initUI() {
        binding.searchView.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?) = false

                @RequiresApi(Build.VERSION_CODES.M)
                override fun onQueryTextChange(newText: String?): Boolean {
                    searchData(newText.orEmpty())
                    return false
                }

            },
        )

        adapter = SuperHeroAdapter {
            navigateToDetailSuperHero(it)
        }
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun searchData(query: String) {
        binding.progressBarView.isVisible = true
        if (Network.networkExists(this)) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    if (query != "") {
                        val res: Response<SuperHeroModel> =
                            retrofit.create(Provider::class.java).getSuperHeroes(query)
                        val response: SuperHeroModel? = res.body()
                        if (response != null) {
                            if (response.response == "error") {
                                runOnUiThread {
                                    binding.progressBarView.isVisible = false
                                }
                                Log.d("ROJO", response.toString())
                            } else {
                                runOnUiThread {
                                    adapter.updateDataList(response.results)
                                    binding.progressBarView.isVisible = false
                                }
                                Log.d("ROJO", response.toString())
                            }
                        }
                    }
                } catch (e: IOException) {
                    runOnUiThread {
                        binding.progressBarView.isVisible = false
                    }
                    Log.d("ROJO", "No se establecio al conexion")
                }
            }
        } else {
            if (flag_message_lost_network) {
                Toast.makeText(this, getString(R.string.lost_connection), Toast.LENGTH_LONG).show()
                flag_message_lost_network = false
            }
        }
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl("https://superheroapi.com/api/YOUR_API_KEY/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun navigateToDetailSuperHero(id: String) {
        val intent = Intent(this, DetailSuperHeroActivity::class.java)
        intent.putExtra(SUPER_HERO_ID, id)
        startActivity(intent)
    }
}