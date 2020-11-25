package org.unquitravel.model.entity

import org.unquitravel.model.exception.ContraseniaIncorrecta
import javax.persistence.*

@Entity
class Usuario(var nombreUsuario: String, val contrasenia: String) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null
    var nombre: String? = null
    var apellido: String? = null
    var edad: Int? = null
    var rol: String? = null
    var email: String? = null

    @OneToMany(mappedBy = "usuario", cascade = [CascadeType.ALL], fetch = FetchType.EAGER, orphanRemoval = true)
    var reservas: MutableList<Reserva> = mutableListOf()

    fun verificarContrasenia(contraseniaLogin: String): Boolean {
        if(this.contrasenia != contraseniaLogin) {
            throw ContraseniaIncorrecta()
        }
        return this.contrasenia == contraseniaLogin
    }
}