package org.unquitravel.model.exception

class UsuarioNoEncontrado(email: String): Exception("Usuario inexistente." )
class ReservaNoEncontrada(message:String): Exception(message)
class ContraseniaIncorrecta: Exception("La contraseña o email ingresados son incorrectos.")
class NotFoundToken: Exception()
