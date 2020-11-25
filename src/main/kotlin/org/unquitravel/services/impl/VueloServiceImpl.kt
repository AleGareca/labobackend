package org.unquitravel.services.impl

import org.unquitravel.dao.hibernate.impl.VueloDAO
import org.unquitravel.model.entity.Vuelo
import org.unquitravel.services.interfaces.IVueloService
import org.unquitravel.services.runner.TransactionRunner

class VueloServiceImpl(private val vueloDao: VueloDAO) : IVueloService {
    override fun recuperar(id: Int?): Vuelo {
        return TransactionRunner.runTrx { vueloDao.recuperar(id) }
    }

    override fun recuperarTodos(): List<Vuelo> {
        return TransactionRunner.runTrx { vueloDao.recuperarTodos() }
    }

    override fun guardar(item: Vuelo) {
        TransactionRunner.runTrx { vueloDao.guardar(item) }
    }

    override fun agregarCantidadDeVuelos(id: Int?, cant: Int) {
        TransactionRunner.runTrx { vueloDao.agregarCantidadVuelos(id,cant) }
    }
}
