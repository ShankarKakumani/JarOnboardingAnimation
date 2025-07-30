package com.shankarkakumani.data.client.ktor.client

// Note: Using debug logging as default for development
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KtorClientProvider @Inject constructor() {
    
    fun provideHttpClient(): HttpClient {
        return HttpClient(Android) {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
            
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL  // Enable for development
            }
            
            install(HttpTimeout) {
                connectTimeoutMillis = KtorConfig.CONNECT_TIMEOUT
                requestTimeoutMillis = KtorConfig.REQUEST_TIMEOUT
                socketTimeoutMillis = KtorConfig.SOCKET_TIMEOUT
            }
            
            defaultRequest {
                url(KtorConfig.BASE_URL)
            }
        }
    }
} 