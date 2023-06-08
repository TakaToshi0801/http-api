package com.example.dao

import com.example.models.*

interface DAOFacade {
    suspend fun allCustomers(): List<Customer>
    suspend fun customer(id: Int): Customer?
    suspend fun addNewCustomer(firstName: String, lastName: String, email: String): Customer?
    suspend fun editCustomer(id: Int, firstName: String, lastName: String, email: String): Boolean
    suspend fun deleteCustomer(id: Int): Boolean
}