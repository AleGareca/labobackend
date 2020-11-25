package org.unquitravel.controller

import org.unquitravel.handler.Handler
import io.javalin.http.Context
import org.unquitravel.dao.hibernate.impl.UsuarioDAO
import org.unquitravel.model.exception.ContraseniaIncorrecta
import org.unquitravel.model.exception.UsuarioNoEncontrado
import org.unquitravel.services.impl.UsuarioServiceImpl
import org.unquitravel.validator.Validator

class LoginController(private val tokenJWT: TokenJWT) {

    private val usuarioService = UsuarioServiceImpl(UsuarioDAO())

    fun loginUser(ctx: Context) {
        val authentication = Validator(ctx)
        val loginUser = authentication.validatorLoginUser()
        try {
            usuarioService.existeUsuario(loginUser.email)
            val usuario = usuarioService.recuperarPorEmail(loginUser.email)
            usuario.verificarContrasenia(usuario.contrasenia)
            ctx.header("Authorization", tokenJWT.generateToken(usuario))
            ctx.status(201)
            ctx.json(mapOf("result:" to "ok"))
        } catch (exception: UsuarioNoEncontrado) {
            ctx.status(400)
            ctx.json(Handler(400, "false", exception.message!!))
        } catch (exception: ContraseniaIncorrecta) {
            ctx.status(400)
            ctx.json(Handler(400, "false", exception.message!!))
        } catch (exception: Exception) {
            ctx.status(500)
            ctx.json(Handler(500, "false", exception.message!!))
        }
    }
}
