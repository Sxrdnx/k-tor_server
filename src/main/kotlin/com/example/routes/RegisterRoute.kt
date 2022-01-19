package com.example.routes

import com.example.data.checkIfUSerExists
import com.example.data.collections.User
import com.example.data.registerUser
import com.example.data.requests.AccountRequest
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.features.ContentTransformationException
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.registerRoute(){
    /**
     * se genera un try catch dado que si el contenido del request no es valido se debe manejar el error
     */
    route("/register"){
        post {
            val request = try {
                call.receive<AccountRequest>()
            }catch (e: ContentTransformationException){
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }
            val userExist = checkIfUSerExists(request.email)
            if (!userExist){
                if (registerUser(User(request.email,request.password))){
                    call.respond()
                }
            }
        }
    }
}
