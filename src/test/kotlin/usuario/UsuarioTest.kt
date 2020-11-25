package usuario

import io.javalin.http.Context
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.unquitravel.controller.UsuarioController
import org.unquitravel.model.entity.Usuario
import java.lang.NullPointerException
import kotlin.test.assertEquals

class UsuarioTest {

    val ctx = mockk<Context>(relaxed = true)

    @Test
    fun `verificacion de contrasenia en usuario`() {
        val contrasenia = "muysecreta"
        val usuario = Usuario("longaniza", contrasenia)
        assertEquals(usuario.contrasenia, contrasenia)
    }

    @Test
    fun `verificacion de nombre usuario en usuario`() {
        val nombreUsuario = "longaniza"
        val usuario = Usuario(nombreUsuario, "muysecreta")
        assertEquals(usuario.nombreUsuario, nombreUsuario)
    }

    @Test
    fun `POST obtener info usuario`() {
        val user = Usuario("ptroche", "")
        user.email = "ptroche@gmail.com"
        every { ctx.sessionAttribute<Usuario>("user") } returns user
        UsuarioController().obtenerInfo(ctx)
        verify { ctx.status(200) }
    }

    @Test
    fun `POST obtener vuelos bad request`() {
        every { ctx.sessionAttribute<Usuario>("user") } returns null
        Assertions.assertThrows(NullPointerException::class.java, { UsuarioController().obtenerInfo(ctx) })
    }

    @Test
    fun `POST obtener usuario`() {
        every { ctx.pathParam<Int>(":idUsuario").get() } returns 1
        UsuarioController().obtenerUsuario(ctx)
        verify { ctx.status(200) }
    }

    @Test
    fun `POST obtener usuario bad request`() {
        UsuarioController().obtenerUsuario(ctx)
        verify { ctx.status(500) }
    }
}