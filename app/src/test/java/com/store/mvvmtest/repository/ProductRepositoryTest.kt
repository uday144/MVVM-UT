package com.store.mvvmtest.repository

import com.store.mvvmtest.api.ProductsAPI
import com.store.mvvmtest.models.ProductListItem
import com.store.mvvmtest.utils.NetworkResult
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

class ProductRepositoryTest{

    @Mock
    lateinit var productsAPI: ProductsAPI


    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun testGetProducts_EmptyList() = runTest {
        Mockito.`when`(productsAPI.getProducts()).thenReturn(Response.success(emptyList()))

        val sut = ProductRepository(productsAPI)
        val result = sut.getProducts()
        assertEquals(true, result is NetworkResult.Success)
        assertEquals(0, result.data!!.size)
    }

    @Test
    fun testGetProducts_expectedProductList() = runTest {
        val productList = listOf<ProductListItem>(
            ProductListItem("", "", 1, "", 40.3, "Prod 1"),
            ProductListItem("", "", 2, "", 20.2, "Prod 2")
        )
        Mockito.`when`(productsAPI.getProducts()).thenReturn(Response.success(productList))

        val sut = ProductRepository(productsAPI)
        val result = sut.getProducts()
        assertEquals(true, result is NetworkResult.Success)
        assertEquals(2, result.data!!.size)
        assertEquals("Prod 1", result.data!![0].title)
    }

    @Test
    fun testGetProducts_expectedError() = runTest {
        Mockito.`when`(productsAPI.getProducts()).thenReturn(Response.error(401, "Unauthorized".toResponseBody()))

        val sut = ProductRepository(productsAPI)
        val result = sut.getProducts()
        assertEquals(true, result is NetworkResult.Error)
        assertEquals("Something went wrong", result.message)
    }
}