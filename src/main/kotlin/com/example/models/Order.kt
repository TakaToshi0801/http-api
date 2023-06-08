package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class Order(val date: String, val items: List<OrderItem>)

@Serializable
data class OrderItem(val itemName: String, val amount: Int, val price: Double)

val orderStorage = mutableListOf<Order>()