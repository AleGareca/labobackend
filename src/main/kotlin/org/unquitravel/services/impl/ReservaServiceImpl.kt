package org.unquitravel.services.impl

import org.unquitravel.dao.hibernate.interfaces.ReservaDAO
import org.unquitravel.model.entity.Reserva
import org.unquitravel.services.interfaces.ReservaService
import org.unquitravel.services.runner.TransactionRunner


class ReservaServiceImpl(private val reservaDao: ReservaDAO) : ReservaService {
    override fun agregarReserva(reserva: Reserva) {
        TransactionRunner.runTrx { reservaDao.crearReserva(reserva) }
    }

    override fun recuperar(id: Int?): Reserva {
       return TransactionRunner.runTrx { reservaDao.recuperar(id)}
    }

    override fun recuperarTodos(): List<Reserva> {
        return TransactionRunner.runTrx{reservaDao.recuperarTodos()}
    }

    override fun eliminar(item: Reserva) {
        return TransactionRunner.runTrx { reservaDao.eliminar(item) }
    }

    override fun guardar(item: Reserva) {
        TransactionRunner.runTrx { reservaDao.guardar(item) }
    }

    override fun eliminarReserva(reserva: Reserva) {
        TransactionRunner.runTrx { reservaDao.eliminarReserva(reserva) }
    }
}
