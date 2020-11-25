package org.unquitravel.dao.hibernate.interfaces

import org.unquitravel.model.entity.Vuelo

interface IVueloDAO {
    fun recuperar(id: Int?): Vuelo
    fun recuperarTodos(): List<Vuelo>
    fun guardar(item: Vuelo)
    fun agregarCantidadVuelos(id: Int?, cant: Int)
}
