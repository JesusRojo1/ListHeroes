package com.jesus_rojo_proyects.superherolist.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jesus_rojo_proyects.superherolist.R
import com.jesus_rojo_proyects.superherolist.models.Results
import com.jesus_rojo_proyects.superherolist.viewholders.SuperHeroViewHolder

class SuperHeroAdapter(
    var superHeroList: List<Results> = emptyList(),
    private val onItemSelected: (String) -> Unit
) :
    RecyclerView.Adapter<SuperHeroViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun updateDataList(superHeroList: List<Results>) {
        this.superHeroList = superHeroList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuperHeroViewHolder {
        return SuperHeroViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_superhero, parent, false)
        )
    }

    override fun onBindViewHolder(holder: SuperHeroViewHolder, position: Int) {
        holder.bind(superHeroList[position], onItemSelected)
    }

    override fun getItemCount() = superHeroList.size


}