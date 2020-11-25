package org.unquitravel.dao.hibernate.generic

import org.unquitravel.model.exception.ReservaNoEncontrada
import org.unquitravel.services.runner.impl.HibernateTransaction

open class HibernateDAO<T>(private val entityType: Class<T>) {

    fun guardar(item: T) {
        val session = HibernateTransaction.currentSession
        session.save(item)
    }

    open fun recuperar(id: Int?): T {
        val session = HibernateTransaction.currentSession
        return session.get(entityType, id) ?: throw Exception()
    }
    fun eliminar(item: T){
        val session = HibernateTransaction.currentSession
        session.delete(item)
    }
}
