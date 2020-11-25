package org.unquitravel.services.interfaces

import org.unquitravel.model.entity.Usuario

interface UsuarioService {
//    fun crear(especie: Usuario)
//    fun actualizar(especie: Especie)
    fun recuperar(id: Int?): Usuario
    fun recuperarTodos(): List<Usuario>
    fun guardar(usuario: Usuario)
    fun existeUsuario(email: String): Boolean
    fun recuperarPorEmail(email: String): Usuario
//    fun eliminarTodos()
}

