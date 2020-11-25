package dao


import io.javalin.http.Context
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.unquitravel.controller.ReservaController
import org.unquitravel.dao.hibernate.impl.ReservaDAO
import org.unquitravel.dao.hibernate.impl.UsuarioDAO
import org.unquitravel.dao.hibernate.impl.VueloDAO
import org.unquitravel.handler.ReservaHandler
import org.unquitravel.model.entity.Reserva
import org.unquitravel.model.entity.Usuario
import org.unquitravel.model.exception.ReservaNoEncontrada
import org.unquitravel.services.impl.ReservaServiceImpl
import org.unquitravel.services.impl.UsuarioServiceImpl
import org.unquitravel.services.impl.VueloServiceImpl
import utils.DataServiceHibernate
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals


class ReservaTest {
    private var vueloService = VueloServiceImpl(VueloDAO())
    private var dataService = DataServiceHibernate()
    private var reservaService = ReservaServiceImpl(ReservaDAO())
    private var usuarioService = UsuarioServiceImpl(UsuarioDAO())
    private var nuevoUsuario = Usuario("test", "test")
    val ctx = mockk<Context>(relaxed = true)

    @BeforeEach
    fun crearDatos() {
        nuevoUsuario.email = "test@gmail.com"
        usuarioService.guardar(nuevoUsuario)
        dataService.crearSetDeDatosIniciales()
    }
    @Test
    fun crearReserva(){
        val vuelo = vueloService.recuperar(1)
        val reserva = Reserva(nuevoUsuario, vuelo)
        reservaService.agregarReserva(reserva)
        val reservaRecuperda = reservaService.recuperar(reserva.id)
        assertEquals(reserva.id!!, reservaRecuperda.id!!)

    }
    @Test
    fun decremetarStockDeVuelos(){
        vueloService.agregarCantidadDeVuelos(2, 2)
        val vuelo = vueloService.recuperar(2)
        val reserva = Reserva(nuevoUsuario, vuelo)
        val cantiadDeVuelosIniciales = vuelo.cantidadReservas
        reservaService.agregarReserva(reserva)
        val cantidadDeVuelosActuales = vueloService.recuperar(vuelo.id).cantidadReservas
        assertEquals(cantiadDeVuelosIniciales - 1, cantidadDeVuelosActuales)

    }

    @Test
    fun eliminarReserva(){
        val vuelo = vueloService.recuperar(1)
        val reserva = Reserva(nuevoUsuario, vuelo)
        reservaService.guardar(reserva)
        reservaService.eliminarReserva(reserva)
        assertThrows(ReservaNoEncontrada::class.java) {reservaService.recuperar(reserva.id)}

    }
    @Test
    fun incremetarStockDeVuelos(){
        vueloService.agregarCantidadDeVuelos(3, 3)
        val vuelo = vueloService.recuperar(3)
        val reserva = Reserva(nuevoUsuario, vuelo)
        val cantiadDeVuelosIniciales = vuelo.cantidadReservas
        reservaService.eliminarReserva(reserva)
        val cantidadDeVuelosActuales = vueloService.recuperar(vuelo.id).cantidadReservas
        assertNotEquals(cantiadDeVuelosIniciales, cantidadDeVuelosActuales)
    }

    @Test
    fun recuperarReservaInexistente() {
         assertThrows(ReservaNoEncontrada::class.java) { reservaService.recuperar(999) }
    }
    @AfterEach
    fun eliminar(){

    }

    @Test
    fun `POST crear reserva`() {
        every { ctx.bodyValidator(ReservaHandler::class.java).get() } returns ReservaHandler(1,1)
        ReservaController().crearReserva(ctx)
        verify { ctx.status(201) }
    }

    @Test
    fun `POST eliminar reserva`() {
        val reservaEliminar = reservaService.recuperarTodos()[0]
        every { ctx.pathParam<Int>(":idReserva").get() } returns reservaEliminar.id!!
        ReservaController().eliminarReserva(ctx)
        verify { ctx.status(201) }
    }

    @Test
    fun `POST eliminar reserva bad request`() {
        every { ctx.pathParam<Int>(":idReserva").get() } returns 0
        ReservaController().eliminarReserva(ctx)
        verify { ctx.status(404) }
    }
}
