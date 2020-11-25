package org.unquitravel

import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*
import io.javalin.core.security.Role
import io.javalin.core.util.RouteOverviewPlugin
import org.unquitravel.controller.*

enum class Roles : Role {
    ANYONE, USER
}

fun main(args: Array<String>) {
    ApiWallet(7000).init()
}

class ApiWallet(private val port: Int) {

    fun init(): Javalin {

        val tokenJWT = TokenJWT()
        val jwtAccessManager = JWTAccessManagers(tokenJWT)

        val app = Javalin.create {
            it.enableCorsForAllOrigins()
            it.registerPlugin(RouteOverviewPlugin("/routes"))
            it.accessManager(jwtAccessManager)

        }.exception(Exception::class.java) { e, ctx ->
            e.printStackTrace()
            ctx.status(500)
            ctx.json(e.message!!)
        }.start(port)

        app.before {
            it.header("Access-Control-Expose-Headers", "*")
        }

        val vueloController = VueloController()
        val reservaController = ReservaController()


        app.routes {
            path("/vuelos") {
                get(vueloController::obtenerVuelos, mutableSetOf<Role>(Roles.ANYONE))
                path(":idVuelo") {
                    get(vueloController::obtenerVuelo, mutableSetOf<Role>(Roles.ANYONE))
                }
                path("/buscar") {
                    post(vueloController::buscarVuelos, mutableSetOf<Role>(Roles.ANYONE))
                }
                path("/crear"){
                    post(vueloController::crearVuelo,mutableSetOf<Role>(Roles.ANYONE))
                }
            }

            path("/reservas"){
                post(reservaController::crearReserva, mutableSetOf<Role>(Roles.USER))
            }

            path("/vuelos") {
                path(":idVuelo") {
                    post(vueloController::agregarCantidadDeVuelos, mutableSetOf<Role>(Roles.USER))
                }
            }
        }
        return app
    }
}
