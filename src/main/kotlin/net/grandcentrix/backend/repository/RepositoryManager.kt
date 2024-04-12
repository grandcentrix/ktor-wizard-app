package net.grandcentrix.backend.repository;

interface RepositoryManager<in I, out O,E> {

    // return all elements
    fun getAll(): O

    // return a single element
    fun getItem(item: I): E

    //update element
    fun updateItem(item: I)

    //add element
    fun addItem(item: I)

    //delete element
    fun deleteItem(item: I)

}
