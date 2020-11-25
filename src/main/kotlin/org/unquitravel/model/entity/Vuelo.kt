package org.unquitravel.model.entity

import java.util.Date
import javax.persistence.*

@Entity
class Vuelo(var origen: String, var destino: String, var precio: Double, var fechaSalida: Date) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null
    var disponible = true
    var cantidadReservas = 0
    var duracion = 0

    @OneToMany(mappedBy = "vuelo", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    var reservas: MutableList<Reserva> = mutableListOf()
}