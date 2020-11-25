package org.unquitravel.services.interfaces

import org.unquitravel.model.entity.Vuelo

interface IVueloService {
    fun recuperar(id: Int?): Vuelo
    fun recuperarTodos(): List<Vuelo>
    fun guardar(item: Vuelo)
    fun agregarCantidadDeVuelos(id: Int?,cant:Int)
//    fun eliminarTodos()
}

