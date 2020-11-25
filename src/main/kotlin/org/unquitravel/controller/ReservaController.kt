package org.unquitravel.controller

import io.javalin.http.Context
import org.unquitravel.dao.hibernate.impl.ReservaDAO
import org.unquitravel.dao.hibernate.impl.UsuarioDAO
import org.unquitravel.dao.hibernate.impl.VueloDAO
import org.unquitravel.handler.*
import org.unquitravel.model.entity.Reserva
import org.unquitravel.model.exception.ReservaNoEncontrada
import org.unquitravel.services.impl.ReservaServiceImpl
import org.unquitravel.services.impl.UsuarioServiceImpl
import org.unquitravel.services.impl.VueloServiceImpl

class ReservaController {
    val reservaService = ReservaServiceImpl(ReservaDAO())
    val vueloService = VueloServiceImpl(VueloDAO())
    val usuarioDAO = UsuarioServiceImpl(UsuarioDAO())

    fun crearReserva(ctx: Context){
        val reservaHandler = ctx.bodyValidator(ReservaHandler::class.java).get()
        val vuelo = vueloService.recuperar(reservaHandler.idVuelo)
        val usuario = usuarioDAO.recuperar(reservaHandler.idUsuario)
        val reserva = Reserva(usuario,vuelo)
        reservaService.agregarReserva(reserva)
        ctx.status(201)
        ctx.json(Handler(201,"Create", "La reserva fue creada correctamente"))
    }
    fun eliminarReserva(ctx: Context){
        val id = ctx.pathParam<Int>(":idReserva").get()
        try {
            val reserva = reservaService.recuperar(id)
            reservaService.eliminarReserva(reserva)
            ctx.status(201)
            ctx.json(Handler(201,"Delete", "La reserva con id ${id} fue eliminada"))
        }catch (e: ReservaNoEncontrada){
            ctx.status(404)
            ctx.json(Handler(404,"Delete",e.message!!))
        }
    }
}
