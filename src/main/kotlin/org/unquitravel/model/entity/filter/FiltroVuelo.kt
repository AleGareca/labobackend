package org.unquitravel.model.entity.filter

data class FiltroVuelo(
        val destino: String?,
        val precioMaximo: Double?,
        val precioMinimo: Double?,
        val duracion: Int?
)
