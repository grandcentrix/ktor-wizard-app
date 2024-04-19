package net.grandcentrix.backend.dao

interface DAOFacade<in I,N, out O,E> {
    // return all elements
    suspend fun getAll(): O

    // return a single element
    suspend fun getItem(name: N): E

    //add element
    suspend fun addItem(item: I)

    //update element
    suspend fun updateItem(item: I)

    //delete element
    suspend fun deleteItem(name: N)
//    abstract suspend fun getByEmail(email: N): E

}