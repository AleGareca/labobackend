package dao

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.unquitravel.dao.hibernate.impl.UsuarioDAO
import org.unquitravel.model.entity.Usuario
import org.unquitravel.services.impl.UsuarioServiceImpl

class  UsuarioDAO {

    private val usuarioService = UsuarioServiceImpl(UsuarioDAO())

    @Test
    fun `traer todo`() {
        val todosLosUsuarios = usuarioService.recuperarTodos()
        assertEquals(todosLosUsuarios.count(), todosLosUsuarios.count())
    }

    @Test
    fun `crear usuario`() {
        val nuevoUsuario = Usuario("ptroche", "35")
        nuevoUsuario.email = "ptroche@gmail.com"
        usuarioService.guardar(nuevoUsuario)
        val usuarioBase = usuarioService.recuperar(nuevoUsuario.id)
        assertEquals(usuarioBase.nombreUsuario , nuevoUsuario.nombreUsuario)
    }
}
