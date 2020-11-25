package org.unquitravel.dao.hibernate.impl

import org.unquitravel.dao.hibernate.generic.HibernateDAO
import org.unquitravel.dao.hibernate.interfaces.UsuarioDAO
import org.unquitravel.model.entity.Usuario
import org.unquitravel.services.runner.impl.HibernateTransaction

class UsuarioDAO : HibernateDAO<Usuario>(Usuario::class.java), UsuarioDAO {
    override fun recuperarTodos(): List<Usuario> {
        val session = HibernateTransaction.currentSession
        val hql = ("from Usuario")
        val query = session.createQuery(hql, Usuario::class.java)
        return query.resultList
    }

    override fun existeUsuario(email: String): Boolean {
        val session = HibernateTransaction.currentSession
        val hql = ("from Usuario where email = :email")
        val query = session.createQuery(hql, Usuario::class.java)
        query.setParameter("email", email)
        return query.resultList.count() > 0
    }

    override fun recuperarPorEmail(email: String): Usuario {
        val session = HibernateTransaction.currentSession
        val hql = ("from Usuario where email = :email")
        val query = session.createQuery(hql, Usuario::class.java)
        query.setParameter("email", email)
        return query.singleResult
    }
}