package com.example.routes

import com.example.data.checkIfUSerExists
import com.example.data.collections.User
import com.example.data.registerUser
import com.example.data.requests.AccountRequest
import com.example.data.requests.SimpleResponse
import com.example.security.getHashWithSalt
import io.ktor.application.*
import io.ktor.features.ContentTransformationException
import io.ktor.http.HttpStatusCode.Companion.BadRequest
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.registerRoute(){
    /**
     * se genera un try catch dado que si el contenido del request no es valido se debe manejar el error*
     * El bloque post{} es una corutina por lo que nos permite ejecutar las suspend fun
     */
    route("/register"){
        post {
            val request = try {
                call.receive<AccountRequest>()
            }catch (e: ContentTransformationException){
                call.respond(BadRequest)
                return@post
            }
            val userExist = checkIfUSerExists(request.email)
            if (!userExist){
                if (registerUser(User(request.email,getHashWithSalt(request.password)))){
                    call.respond(OK,SimpleResponse(true,"Successfully created acouny!"))
                }else{
                    call.respond(OK,SimpleResponse(false,"An unknown error occured"))
                }
            }else{
                call.respond(OK,SimpleResponse(false,"A user with that E-mail already exist"))
            }
        }
    }
}
