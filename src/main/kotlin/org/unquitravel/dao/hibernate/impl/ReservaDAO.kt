package org.unquitravel.dao.hibernate.impl

import org.unquitravel.dao.hibernate.generic.HibernateDAO
import org.unquitravel.dao.hibernate.interfaces.ReservaDAO
import org.unquitravel.model.entity.Reserva
import org.unquitravel.model.exception.ReservaNoEncontrada
import org.unquitravel.services.runner.impl.HibernateTransaction


class ReservaDAO: HibernateDAO<Reserva>(Reserva::class.java), ReservaDAO {

    override fun recuperar(id: Int?): Reserva {
        val session = HibernateTransaction.currentSession
        return session.get(Reserva::class.java, id) ?: throw ReservaNoEncontrada("Reserva no encontrada")
    }

    override fun recuperarTodos(): List<Reserva> {
        val session = HibernateTransaction.currentSession
        val hql = ("from Reserva")
        val query = session.createQuery(hql, Reserva::class.java)
        return query.resultList
    }


    override fun crearReserva(reserva: Reserva) {
        val session = HibernateTransaction.currentSession
        reserva.vuelo.cantidadReservas -= 1
        session.update(reserva.vuelo)
        this.guardar(reserva)
    }

    override fun eliminarReserva(reserva: Reserva) {
        val session = HibernateTransaction.currentSession
        val idVuelo= reserva.vuelo.id
        val cantidadDeReserva = reserva.vuelo.cantidadReservas + 1
        val hql = ("UPDATE Vuelo set cantidadReservas = $cantidadDeReserva where id = $idVuelo")
        session.createNativeQuery(hql).executeUpdate()
        session.delete(reserva)
        //reservaAux.cantidadReservas += 1
       // session.update(reservaAux)
    }

}
