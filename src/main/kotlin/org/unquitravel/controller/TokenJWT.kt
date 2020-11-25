package org.unquitravel.controller

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTCreator
import com.auth0.jwt.algorithms.Algorithm
import javalinjwt.JWTGenerator
import javalinjwt.JWTProvider
import org.unquitravel.model.entity.Usuario
import org.unquitravel.model.exception.NotFoundToken


class UserGenerator : JWTGenerator<Usuario> {
    override fun generate(user: Usuario, algorithm: Algorithm): String {
        val token = JWT.create().withClaim("id", user.id.toString())
        return token.sign(algorithm)
    }
}

class TokenJWT {

    private val algorithm: Algorithm = Algorithm.HMAC256("Clave_Archi_Mega_Secreta")
    private val generator = UserGenerator()
    private val verifier = JWT.require(algorithm).build()
    private val provider = JWTProvider(algorithm, generator, verifier)

    fun generateToken(user: Usuario): String {
        return provider.generateToken(user)
    }

    fun validate(token: String): String {
        val token = provider.validateToken(token)
        if (!token.isPresent) throw NotFoundToken()
        return token.get().getClaim("id").asString()
    }

}