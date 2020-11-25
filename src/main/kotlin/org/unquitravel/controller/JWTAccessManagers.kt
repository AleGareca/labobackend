package org.unquitravel.controller

import io.javalin.core.security.AccessManager
import io.javalin.core.security.Role
import io.javalin.http.Context
import io.javalin.http.Handler
import io.javalin.http.UnauthorizedResponse
import org.unquitravel.Roles
import org.unquitravel.dao.hibernate.impl.UsuarioDAO
import org.unquitravel.model.entity.Usuario
import org.unquitravel.model.exception.NotFoundToken
import org.unquitravel.model.exception.UsuarioNoEncontrado
import org.unquitravel.services.impl.UsuarioServiceImpl

class JWTAccessManagers(private val tokenJWT: TokenJWT) : AccessManager {

    private val usuarioService = UsuarioServiceImpl(UsuarioDAO())
    private var usuarios: List<Usuario> = mutableListOf()
    init {
        usuarios = usuarioService.recuperarTodos()
    }

    private fun getUser(token: String): Usuario {
        try {
            usuarios = usuarioService.recuperarTodos()
            val userId = tokenJWT.validate(token)
            return usuarios.find { it.id == userId.toInt() }!!
        } catch (e: NotFoundToken) {
            throw UnauthorizedResponse("Bad token or not found")
        } catch (e: UsuarioNoEncontrado) {
            throw UnauthorizedResponse("Invalid Token")
        }
    }

    override fun manage(handler: Handler, ctx: Context, roles: MutableSet<Role>) {
        val token = ctx.header("Authorization")
        when {
            token == null && roles.contains(Roles.ANYONE) -> handler.handle(ctx)
            token == null -> throw UnauthorizedResponse("Token not found")
            roles.contains(Roles.ANYONE) -> handler.handle(ctx)
            roles.contains(Roles.USER) -> {
                ctx.sessionAttribute("user", getUser(token))
                handler.handle(ctx)
            }
        }
    }
}