package com.example.routes

import com.example.data.checkPassworForEmail
import com.example.data.requests.AccountRequest
import com.example.data.requests.SimpleResponse
import io.ktor.application.*
import io.ktor.features.ContentTransformationException
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.loginRoute(){
    route("/login"){
        post {
            val request = try {
                call.receive<AccountRequest>()
            }catch (e: ContentTransformationException){
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }
            val isPasswordCorrect = checkPassworForEmail(request.email,request.password)
            if (isPasswordCorrect){
                call.respond(HttpStatusCode.OK,SimpleResponse(true,"Your are now logged in!"))
            }else{
                call.respond(HttpStatusCode.OK,SimpleResponse(false,"the email or password is incorrect"))
            }
        }
    }
}