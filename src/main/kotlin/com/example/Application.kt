package com.example


import com.example.data.checkPassworForEmail
import com.example.routes.loginRoute
import com.example.routes.noteRoutes
import com.example.routes.registerRoute

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.routing.*

/**
 * Intancia de nuestro servidor
 */
fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
@JvmOverloads
fun Application.module(testing: Boolean = false) {
    /**
     * Funcion de extencion que extiende de application y se coloca la configuracion del servidor llamando a
     * los bloques que lo contengan
     * defoult headers- se podra interseptar respuestas con informacion adicional en los headers
     * callLogging-filtra las peticiones y respuestas a consideracion de uso
     * Routing- vericar que se conectaron a los endpoins
     * ContentNegotation- (la negociación de contenido es el mecanismo que se utiliza
     * para servir diferentes representaciones de un recurso al mismo URI para ayudar al agente de usuario a especificar qué
     * representación es la más adecuada para el usuario (por ejemplo, qué idioma
     * del documento, qué formato de imagen o qué codificación de contenido) .
     */
    install(DefaultHeaders)
    install(CallLogging)
    install(ContentNegotiation){
        gson {
            setPrettyPrinting()//pa verse bonitos los json
        }
    }
    install(Authentication){
        configureAuth()
    }

    install(Routing){
        registerRoute()// configuracion necesaria para que nuestra ruta esta disponible
        loginRoute()
        noteRoutes()
    }
}


private fun Authentication.Configuration.configureAuth(){
    basic {
        realm = "Note Server" //nombre del servidor
        validate { credentials ->
            //definimos la logica para autentificar el accesos a nuestras apis
            val email = credentials.name
            val password = credentials.password
            if (checkPassworForEmail(email,password)){
                UserIdPrincipal(email)
            }else null
        }
    }

}
