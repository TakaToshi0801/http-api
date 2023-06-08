package com.example.routes

import com.example.dao.*
import com.example.models.*
import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

fun Route.customerRouting() {
    route("/customer") {
        get {
            if (dao.allCustomers().isNotEmpty()) {
                call.respond(dao.allCustomers())
            } else {
                call.respondText("No customer found", status = HttpStatusCode.OK)
            }
        }
        get("{id?}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id != null) {
                val targetCustomer = dao.customer(id)
                if (targetCustomer != null) {
                    call.respond(targetCustomer)
                } else {
                    call.respondText("No customer found with id: $id", status = HttpStatusCode.NotFound)
                }
            } else {
                call.respondText("Invalid id", status = HttpStatusCode.BadRequest)
            }
        }
        post {
            val customer = call.receive<Customer>()
            val addedCustomer = dao.addNewCustomer(customer.firstName, customer.lastName, customer.email)
            if (addedCustomer != null) {
                call.respondText("Customer stored correctly", status = HttpStatusCode.Created)
            } else {
                call.respondText("Failed to add new customer", status = HttpStatusCode.InternalServerError)
            }
        }
        delete("{id?}") {
            val id = call.parameters["id"] ?.toIntOrNull()
            if (id != null) {
                val deleteCustomer = dao.deleteCustomer(id)
                if (deleteCustomer) {
                    call.respondText("Customer deleted", status = HttpStatusCode.Accepted)
                } else {
                    call.respondText("Failed to delete customer", status = HttpStatusCode.InternalServerError)
                }
            } else {
                call.respondText("Invalid id", status = HttpStatusCode.BadRequest)
            }
        }
    }
}