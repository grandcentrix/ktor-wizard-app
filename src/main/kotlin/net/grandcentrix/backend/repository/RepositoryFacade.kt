package net.grandcentrix.backend.repository;

interface RepositoryFacade<O,T> {

    // return all elements
    fun getAll(): List<O>

    // return a single element
    fun getItem(id: String): T

    //update element
//    fun updateItem(item: I)

    //add element
//    fun addItem(item: I)

    //delete element
//    fun deleteItem(name: T)


}
