package net.grandcentrix.backend.repository;

interface RepositoryFacade<in T, out O,E> {

    // return all elements
    fun getAll(): O

    // return a single element
    fun getItem(name: T): E

    //update element
//    fun updateItem(item: I)

    //add element
//    fun addItem(item: I)

    //delete element
//    fun deleteItem(name: T)


}
