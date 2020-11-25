package org.unquitravel.handler

import java.util.*

data class UserLogin(val email: String, val password: String)
open class Handler(val code: Int, val type: String, open val message: String)
open class CantidadDeVuelos(val cantReservas :Int)
open class ReservaHandler(val idVuelo: Int, val idUsuario:Int)
open class VueloHandler(val origen: String, val destino: String, val precio: Double, val fechaSalida: String, val disponible:Boolean,
                        val cantidadReservas:Int,val duracion:Int)

