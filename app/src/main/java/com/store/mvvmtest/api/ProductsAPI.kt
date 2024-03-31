package com.store.mvvmtest.api

import com.store.mvvmtest.models.ProductListItem
import retrofit2.Response
import retrofit2.http.GET

interface ProductsAPI {

    @GET("/products")
    suspend fun getProducts() : Response<List<ProductListItem>>

}