package org.unquitravel.model.entity

import javax.persistence.*

@Entity
class Reserva(@ManyToOne
              var usuario: Usuario,
              @ManyToOne
              var vuelo: Vuelo
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null
}
