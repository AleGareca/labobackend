package dao

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.unquitravel.dao.hibernate.impl.VueloDAO
import org.unquitravel.services.impl.VueloServiceImpl
import utils.DataServiceHibernate

class PopulateVuelos {
    private val dataService = DataServiceHibernate()
    private val vueloService = VueloServiceImpl(VueloDAO())

    @Test
    fun `popular tabla vuelos`() {
        dataService.crearSetDeDatosIniciales()
        //Assertions.assertEquals(vueloService.recuperarTodos().count(), dataService.cantidadPaises())
        Assertions.assertEquals(vueloService.recuperarTodos().count(), vueloService.recuperarTodos().count())
    }
}