package org.unquitravel.model.mapper

import java.util.*

data class UsuarioMapper(  val id: Int?,
                           val nombre: String?,
                           val apellido: String?,
                           val edad: Int?,
                           val rol: String?,
                           val email: String?,
                           val nombreUsuario: String?,
                           val reservas: List<ReservaMapper>
)

data class ReservaMapper(
    val id: Int?,
    val vuelo: VueloMapper?
)

data class VueloMapper(
    val id: Int?,
    val origen: String?,
    val destino: String?,
    val fechaSalida: Date?,
    val disponible: Boolean?,
    val cantidadReservas: Int?,
    val precio: Double,
    val duracion: Int?
){

}

