package org.unquitravel.services.runner

import org.unquitravel.services.runner.impl.HibernateTransaction
import org.unquitravel.services.runner.interfaces.Transaction

object TransactionRunner {
    private var transactions: MutableList<Transaction> = mutableListOf(HibernateTransaction())

    fun <T> runTrx(bloque: () -> T): T {
        try {
            transactions.forEach { it.start() }
            val result = bloque()
            transactions.forEach { it.commit() }
            return result
        } catch (exception: Throwable) {
            transactions.forEach { it.rollback() }
            throw exception
        }
    }

}