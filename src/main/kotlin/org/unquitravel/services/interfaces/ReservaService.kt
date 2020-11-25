package org.unquitravel.services.interfaces

import org.unquitravel.model.entity.Reserva

interface ReservaService {
    fun agregarReserva(reserva:Reserva)
    fun recuperar(id: Int?): Reserva
    fun recuperarTodos(): List<Reserva>
    fun eliminar(item: Reserva)
    fun guardar(item: Reserva)
    fun eliminarReserva(reserva: Reserva)
}
