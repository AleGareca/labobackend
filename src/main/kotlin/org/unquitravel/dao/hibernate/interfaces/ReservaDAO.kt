package org.unquitravel.dao.hibernate.interfaces

import org.unquitravel.model.entity.Reserva

interface ReservaDAO {
    fun recuperar(id: Int?): Reserva
    fun recuperarTodos(): List<Reserva>
    fun guardar(item: Reserva)
    fun eliminar(item:Reserva)
    fun crearReserva(reserva: Reserva)
    fun eliminarReserva(reserva: Reserva)
}
