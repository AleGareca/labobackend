package org.unquitravel.services.impl

import org.unquitravel.dao.hibernate.interfaces.UsuarioDAO
import org.unquitravel.model.entity.Usuario
import org.unquitravel.model.exception.UsuarioNoEncontrado
import org.unquitravel.services.interfaces.UsuarioService
import org.unquitravel.services.runner.TransactionRunner

class UsuarioServiceImpl(private val usuarioDao: UsuarioDAO) : UsuarioService {
    override fun recuperar(id: Int?): Usuario {
        return TransactionRunner.runTrx { usuarioDao.recuperar(id) }
    }

    override fun recuperarTodos(): List<Usuario> {
        return TransactionRunner.runTrx { usuarioDao.recuperarTodos() }
    }

    override fun guardar(usuario: Usuario) {
        TransactionRunner.runTrx { usuarioDao.guardar(usuario) }
    }

    override fun existeUsuario(email: String): Boolean {
        val existeUsuario = TransactionRunner.runTrx {  usuarioDao.existeUsuario(email) }
        if (!existeUsuario) {
            throw UsuarioNoEncontrado(email)
        }
        return existeUsuario
    }

    override fun recuperarPorEmail(email: String): Usuario {
        return TransactionRunner.runTrx { usuarioDao.recuperarPorEmail(email) }
    }
}