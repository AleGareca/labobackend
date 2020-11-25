package org.unquitravel.validator

import io.javalin.http.Context
import org.unquitravel.handler.UserLogin

class Validator(ctx: Context) {
    val context = ctx
    fun validatorLoginUser(): UserLogin {
        val userLogin = context.bodyValidator(UserLogin::class.java)
            .check({ it.email.isNotBlank() }, "Email can't be empty ")
            .check({ it.password.isNotBlank() }, "Password can't be empty")
            //.check({ it.password.matches(Regex("^[a-zA-Z*]*$")) }, "Password Invalid Format ")
            .get()
        return userLogin
    }
}
