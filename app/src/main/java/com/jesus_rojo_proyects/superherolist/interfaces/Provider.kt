package com.jesus_rojo_proyects.superherolist.interfaces

import com.jesus_rojo_proyects.superherolist.models.SuperHeroDetailModel
import com.jesus_rojo_proyects.superherolist.models.SuperHeroModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface Provider {

    @GET("search/{name}")
    suspend fun getSuperHeroes(@Path("name") superHeroName:String):Response<SuperHeroModel>

    @GET("{id}")
    suspend fun getSuperHeroesId(@Path("id") superHeroId:String):Response<SuperHeroDetailModel>
}