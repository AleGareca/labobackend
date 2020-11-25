package org.unquitravel.controller

import org.unquitravel.handler.Handler
import io.javalin.http.Context
import org.unquitravel.dao.hibernate.impl.VueloDAO
import org.unquitravel.handler.CantidadDeVuelos
import org.unquitravel.handler.ReservaHandler
import org.unquitravel.handler.VueloHandler
import org.unquitravel.model.entity.Vuelo
import org.unquitravel.model.entity.filter.FiltroVuelo
import org.unquitravel.model.mapper.VueloMapper
import org.unquitravel.services.impl.VueloServiceImpl
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class VueloController {

    private val vueloService = VueloServiceImpl(VueloDAO())

    fun crearVuelo(ctx: Context){
        val vueloH = ctx.bodyValidator(VueloHandler::class.java).get()
        val date = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(vueloH.fechaSalida)
       val vuelo= Vuelo(vueloH.origen,vueloH.destino,vueloH.precio,date)
        vuelo.cantidadReservas = vueloH.cantidadReservas
        vuelo.disponible = vueloH.disponible
        vuelo.cantidadReservas =vueloH.cantidadReservas
        vuelo.duracion = vueloH.duracion
        vueloService.guardar(vuelo)
        ctx.status(201)
        ctx.json(Handler(201,"Successful","El vuelo con id ${vuelo.id} fue creado correctamente"))

    }

    fun obtenerVuelos(ctx: Context) {

        try {
            val vuelosMapper = vueloService.recuperarTodos().map { VueloMapper(it.id, it.origen, it.destino, it.fechaSalida, it.disponible, it.cantidadReservas, it.precio, it.duracion) }
            ctx.status(200)
            ctx.json(vuelosMapper)
        }
        catch (exception: Exception) {
            ctx.status(500)
            ctx.json(Handler(500,"false", exception.message!!))
        }
    }

    fun buscarVuelos(ctx: Context) {
        try {
            val filtros = ctx.bodyValidator(FiltroVuelo::class.java).get()
            val vuelosMapper = vueloService.recuperarTodos().map { VueloMapper(it.id, it.origen, it.destino, it.fechaSalida, it.disponible, it.cantidadReservas, it.precio, it.duracion) }
            var vuelosFiltrados = if (filtros.destino != null) vuelosMapper.filter { it.destino == filtros.destino } else vuelosMapper

            if (filtros.precioMaximo == null && filtros.precioMinimo != null){
                ctx.status(400)
                ctx.json("Debe ingresar el valor máximo para el vuelo")
                return
            } else if (filtros.precioMaximo != null) {
                vuelosFiltrados = vuelosFiltrados.filter { it.precio <= filtros.precioMaximo }
                vuelosFiltrados = if (filtros.precioMinimo != null) vuelosFiltrados.filter { it.precio >= filtros.precioMinimo } else vuelosFiltrados.filter { it.precio >0 }
            }

            if (filtros.duracion != 0 && filtros.duracion != null) {
                vuelosFiltrados = vuelosFiltrados.filter { it.duracion!! <= filtros.duracion }
            } else if(filtros.duracion == 0) {
                ctx.status(400)
                ctx.json("El valor no puede ser cero")
                return
            }

            ctx.status(200)
            ctx.json(vuelosFiltrados)
        }
        catch (exception: Exception) {
            ctx.status(500)
            ctx.json(Handler(500,"false", exception.message!!))
        }
    }

    fun obtenerVuelo(ctx: Context) {

        try {
            val contendId = ctx.pathParam<Int>(":idVuelo").get()
            val vueloObtenido = vueloService.recuperar(contendId)

            ctx.status(200)
            ctx.json(VueloMapper(
                    vueloObtenido.id,
                    vueloObtenido.origen,
                    vueloObtenido.destino,
                    vueloObtenido.fechaSalida,
                    vueloObtenido.disponible,
                    vueloObtenido.cantidadReservas,
                    vueloObtenido.precio,
                    vueloObtenido.duracion)
            )
        }
        catch (exception: Exception) {
            ctx.status(500)
            ctx.json(Handler(500,"false", exception.message!!))
        }
    }
    fun agregarCantidadDeVuelos(ctx: Context) {

        try {
            val cantVuelos = ctx.bodyValidator(CantidadDeVuelos::class.java).get()
            val id = ctx.pathParam<Int>(":idVuelo").get()
            vueloService.agregarCantidadDeVuelos(id,cantVuelos.cantReservas)

            ctx.status(201)
            ctx.json(Handler(201,"Successful","La cantidad de usuarios fue atualizada correctamente"))
        }
        catch (exception: Exception) {
            ctx.status(401)
            ctx.json(Handler(401,"false", exception.message!!))
        }
    }

}
