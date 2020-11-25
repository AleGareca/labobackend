package org.unquitravel

import org.unquitravel.model.entity.Reserva
import java.util.*

class UnquiTravel {
    fun realizarReserva(reserva: Reserva) {
        // TODO: Realizar la reserva y presistirla
        // return idReserva
    }
    fun transporteEstaDisponible(idTransporte: Long, fechaInicio: Date, fechaFin: Date) {
        // TODO fijarse en todas las reservas hechas si este transporte esta comprometido
        // return boolean
    }
    fun listarTransportesDisponibles() {
        // TODO retorna una lista de unidadTransporte que tengan disponibilidad en su capacidad de transportar pasajeros
    }
}