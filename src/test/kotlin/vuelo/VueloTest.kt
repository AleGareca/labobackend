package vuelo

import io.javalin.http.Context
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.unquitravel.controller.VueloController
import org.unquitravel.dao.hibernate.impl.ReservaDAO
import org.unquitravel.dao.hibernate.impl.UsuarioDAO
import org.unquitravel.dao.hibernate.impl.VueloDAO
import org.unquitravel.handler.CantidadDeVuelos
import org.unquitravel.model.entity.Reserva
import org.unquitravel.model.entity.Usuario
import org.unquitravel.model.entity.Vuelo
import org.unquitravel.model.entity.filter.FiltroVuelo
import org.unquitravel.services.impl.ReservaServiceImpl
import org.unquitravel.services.impl.UsuarioServiceImpl
import org.unquitravel.services.impl.VueloServiceImpl
import java.lang.NullPointerException
import java.util.*
import kotlin.test.assertEquals

class VueloTest {

    private val vueloService = VueloServiceImpl(VueloDAO())
    private val usuarioService = UsuarioServiceImpl(UsuarioDAO())
    val reservaService = ReservaServiceImpl(ReservaDAO())
    val ctx = mockk<Context>(relaxed = true)

    @Test
    fun `verificacion de origen y destino en vuelo`() {
        val vuelo = Vuelo("BSAS", "Madrid", 200.00, Date())
        assertEquals(vuelo.origen, "BSAS")
        assertEquals(vuelo.destino, "Madrid")
    }

    //(ATTD) Al reservar el vuelo se tiene que guardar en el sistema vinculado con el usuario y decrementando la capacidad del vuelo.
    @Test
    fun `reservar vuelo para un usuario determinado y decrementar capacidad de vuelo`() {

        val nuevoVuelo = Vuelo("BSAS", "Madrid", 200.00, Date())
        vueloService.guardar(nuevoVuelo)

        val nuevoUsuario = Usuario("ptroche", "35")
        nuevoUsuario.email = "ptroche@gmail.com"
        usuarioService.guardar(nuevoUsuario)

        assert(nuevoVuelo.cantidadReservas == 0)

        val reserva = Reserva(nuevoUsuario, nuevoVuelo)
        reservaService.agregarReserva(reserva)

        val usuarioBase = usuarioService.recuperar(nuevoUsuario.id)

        assert(nuevoVuelo.cantidadReservas == -1)
        assert(usuarioBase.reservas.any{ it.id == reserva.id } )
    }

    @Test
    fun `el filtro de vuelo tiene destino`() {
        val filtroVuelo = FiltroVuelo("Argentina", null, null, null)
        assert(filtroVuelo.destino != String())
    }

    @Test
    fun `el filtro de vuelo no tiene destino`() {
        val filtroVuelo = FiltroVuelo("", null, null, null)
        assert(filtroVuelo.destino == String())
    }

    @Test
    fun `el filtro de vuelo tiene precio maximo`() {
        val filtroVuelo = FiltroVuelo("Argentina", 50.0, null, null)
        assert(filtroVuelo.precioMaximo != 0.00)
    }

    @Test
    fun `el filtro de vuelo tiene precio minimo`() {
        val filtroVuelo = FiltroVuelo("Argentina", 50.0, 10.0, null)
        assert(filtroVuelo.precioMinimo != 0.00)
    }

    @Test
    fun `POST obtener vuelos`() {
        every { ctx.pathParam<Int>(":idVuelo").get() } returns 1
        VueloController().obtenerVuelo(ctx)
        verify { ctx.status(200) }
    }

    @Test
    fun `POST obtener vuelos bad request`() {
        every { ctx.pathParam<Int>(":idVuelo").get() } returns 0
        Assertions.assertThrows(NullPointerException::class.java, { VueloController().obtenerVuelo(ctx) })
    }

    @Test
    fun `POST buscar por destino`() {
        every { ctx.bodyValidator(FiltroVuelo::class.java).get() } returns FiltroVuelo("Argentina", null,null,null)
        VueloController().buscarVuelos(ctx)
        verify { ctx.status(200) }
    }

    @Test
    fun `POST buscar por rango precio maximo`() {
        every { ctx.bodyValidator(FiltroVuelo::class.java).get() } returns FiltroVuelo("Argentina", 50.0,null,null)
        VueloController().buscarVuelos(ctx)
        verify { ctx.status(200) }
    }

    @Test
    fun `POST buscar por rango precio maximo y minimo`() {
        every { ctx.bodyValidator(FiltroVuelo::class.java).get() } returns FiltroVuelo("Argentina", 50.0,10.0,null)
        VueloController().buscarVuelos(ctx)
        verify { ctx.status(200) }
    }

    @Test
    fun `POST buscar por rango precio maximo y minimo bad request`() {
        every { ctx.bodyValidator(FiltroVuelo::class.java).get() } returns FiltroVuelo("Argentina", null, 50.0,null)
        VueloController().buscarVuelos(ctx)
        verify { ctx.status(400) }
    }

    @Test
    fun `POST buscar por duracion`() {
        every { ctx.bodyValidator(FiltroVuelo::class.java).get() } returns FiltroVuelo("Argentina", null, null,1)
        VueloController().buscarVuelos(ctx)
        verify { ctx.status(200) }
    }

    @Test
    fun `POST buscar por duracion bad request`() {
        every { ctx.bodyValidator(FiltroVuelo::class.java).get() } returns FiltroVuelo("Argentina", null, 50.0,0)
        VueloController().buscarVuelos(ctx)
        verify { ctx.status(400) }
    }

    @Test
    fun `POST agregar cantidad de vuelos`() {
        every { ctx.bodyValidator(CantidadDeVuelos::class.java).get() } returns CantidadDeVuelos(2)
        every { ctx.pathParam<Int>(":idVuelo").get() } returns 1
        VueloController().agregarCantidadDeVuelos(ctx)
        verify { ctx.status(201) }
    }

    @Test
    fun `POST agregar cantidad de vuelos bad request`() {
        every { ctx.bodyValidator(CantidadDeVuelos::class.java).get() } returns CantidadDeVuelos(2)
        every { ctx.pathParam<Int>(":idVuelo").get() } returns 999999
        Assertions.assertThrows(NullPointerException::class.java, { VueloController().agregarCantidadDeVuelos(ctx) })
    }

}