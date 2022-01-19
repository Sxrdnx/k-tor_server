package com.example

import com.example.data.collections.User
import com.example.data.registerUser
import com.google.gson.Gson
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.routing.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Intancia de nuestro servidor
 */
fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
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
    install(Routing)
    install(ContentNegotiation){
        gson {
            setPrettyPrinting()//pa verse bonitos los json
        }
    }
    CoroutineScope(Dispatchers.IO).launch{
        registerUser(
            User("andres@los.com","1234")
        )
    }

}
