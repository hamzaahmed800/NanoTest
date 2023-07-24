package com.nano.test.data.model.product

class AllProductResponse : ArrayList<AllProductResponseItem>()

data class AllProductResponseItem(
    val category: String,
    val description: String,
    val id: Int,
    val image: String,
    val price: Double,
    val rating: Rating,
    val title: String
)

data class Rating(
    val count: Int,
    val rate: Double
)
