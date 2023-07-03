package com.jesus_rojo_proyects.superherolist.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.jesus_rojo_proyects.superherolist.databinding.ItemSuperheroBinding
import com.jesus_rojo_proyects.superherolist.models.Results
import com.jesus_rojo_proyects.superherolist.models.SuperHeroModel
import com.squareup.picasso.Picasso

class SuperHeroViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val binding = ItemSuperheroBinding.bind(view)

    fun bind(superHeroResponse: Results, onItemSelected: (String) -> Unit) {
        binding.superHeroNameView.text = superHeroResponse.name
        binding.superHeroGenderView.text = superHeroResponse.appearance.gender
        binding.superHeroRaceView.text = superHeroResponse.appearance.race
        Picasso.get().load(superHeroResponse.imageUrl.url).into(binding.imageItemHero)
        binding.root.setOnClickListener { onItemSelected(superHeroResponse.id) }
    }
}