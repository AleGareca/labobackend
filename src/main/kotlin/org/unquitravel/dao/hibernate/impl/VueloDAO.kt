package org.unquitravel.dao.hibernate.impl

import org.unquitravel.dao.hibernate.generic.HibernateDAO
import org.unquitravel.dao.hibernate.interfaces.IVueloDAO
import org.unquitravel.model.entity.Vuelo
import org.unquitravel.services.runner.impl.HibernateTransaction

class VueloDAO : HibernateDAO<Vuelo>(Vuelo::class.java), IVueloDAO {
    override fun recuperarTodos(): List<Vuelo> {
        val session = HibernateTransaction.currentSession
        val hql = ("from Vuelo")
        val query = session.createQuery(hql, Vuelo::class.java)
        return query.resultList
    }

    override fun agregarCantidadVuelos(id: Int?, cant: Int) {
        val session = HibernateTransaction.currentSession
        val vuelo = this.recuperar(id)
        vuelo.cantidadReservas = cant
        session.update(vuelo)
    }
}
