package org.unquitravel.dao.hibernate.interfaces

import org.unquitravel.model.entity.Usuario

interface UsuarioDAO {
    fun recuperar(id: Int?): Usuario
    fun recuperarTodos(): List<Usuario>
    fun guardar(item: Usuario)
    fun existeUsuario(email: String): Boolean
    fun recuperarPorEmail(email: String): Usuario
}