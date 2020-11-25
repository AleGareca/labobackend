package org.unquitravel.controller

import org.unquitravel.handler.Handler
import io.javalin.http.Context
import org.unquitravel.dao.hibernate.impl.UsuarioDAO
import org.unquitravel.model.entity.Usuario
import org.unquitravel.model.mapper.ReservaMapper
import org.unquitravel.model.mapper.UsuarioMapper
import org.unquitravel.model.mapper.VueloMapper
import org.unquitravel.services.impl.UsuarioServiceImpl

class UsuarioController {

    private val usuarioService = UsuarioServiceImpl(UsuarioDAO())

    fun obtenerInfo(ctx: Context) {
        val userToken = ctx.sessionAttribute<Usuario>("user")!!
        val userMapper = UsuarioMapper(
                userToken.id,
                userToken.nombre,
                userToken.apellido,
                userToken.edad,
                userToken.rol,
                userToken.email,
                userToken.nombreUsuario,
                userToken.reservas.map {
                    ReservaMapper(
                            it.id,
                            VueloMapper(
                                    it.vuelo.id,
                                    it.vuelo.origen,
                                    it.vuelo.destino,
                                    it.vuelo.fechaSalida,
                                    it.vuelo.disponible,
                                    it.vuelo.cantidadReservas,
                                    it.vuelo.precio,
                                    it.vuelo.duracion)
                    )
                }
        )
        ctx.status(200)
        ctx.json(userMapper)
    }

    fun obtenerUsuario(ctx: Context) {
        try {
            val contendId = ctx.pathParam<Int>(":idUsuario").get()
            val user = usuarioService.recuperar(contendId)

            ctx.status(200)
            ctx.json(
                    UsuarioMapper(
                            user.id,
                            user.nombre,
                            user.apellido,
                            user.edad,
                            user.rol,
                            user.email,
                            user.nombreUsuario,
                            user.reservas.map {
                                ReservaMapper(
                                        it.id,
                                        VueloMapper(
                                                it.vuelo.id,
                                                it.vuelo.origen,
                                                it.vuelo.destino,
                                                it.vuelo.fechaSalida,
                                                it.vuelo.disponible,
                                                it.vuelo.cantidadReservas,
                                                it.vuelo.precio,
                                                it.vuelo.duracion)
                                )
                            }
                    )
            )
        } catch (exception: Exception) {
            ctx.status(500)
            ctx.json(Handler(500, "false", exception.message!!))
        }
    }
}
