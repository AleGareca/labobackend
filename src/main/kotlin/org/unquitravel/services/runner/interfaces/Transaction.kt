package org.unquitravel.services.runner.interfaces

interface Transaction {
    fun start()
    fun commit()
    fun rollback()
}