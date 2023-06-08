package com.example.routes

import com.example.models.Order
import com.example.models.orderStorage
import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

// 全ての注文を取得
fun Route.listOrderRoute() {
    get("/order") {
        if (orderStorage.isNotEmpty()) {
            call.respond(orderStorage)
        }
    }
}

// 個別の注文を取得
fun Route.getOrderRoutes() {
    get("/order/{id?}") {
        val id = call.parameters["id"] ?: return@get call.respondText(
            "Bad Request", status = HttpStatusCode.BadRequest
        )
        val order = orderStorage.find { it.date == id } ?: return@get call.respondText(
            "Not Found",
            status = HttpStatusCode.NotFound
        )
        call.respond(order)
    }
}

// 注文を作成
fun Route.createOrder() {
    post("/order/create") {
        val order = call.receive<Order>()
        orderStorage.add(order)
        call.respondText("Order stored correctly", status = HttpStatusCode.Created)
    }
}

// 注文の合計金額を取得
fun Route.totalizeOrderRoute() {
    get("/order/{id?}/total") {
        val id = call.parameters["id"] ?: return@get call.respondText(
            "Bad Request",
            status = HttpStatusCode.BadRequest
        )
        val order = orderStorage.find { it.date == id} ?: return@get call.respondText(
            "Not Found",
            status = HttpStatusCode.NotFound
        )
        val total = order.items.sumOf { it.price * it.amount }
        call.respond(total)
    }
}