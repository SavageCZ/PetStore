package cz.mendelu.pef.compose.petstore.communication

import cz.mendelu.pef.compose.petstore.architecture.CommunicationResult
import cz.mendelu.pef.compose.petstore.models.Pet
import retrofit2.Response
import retrofit2.http.*

interface PetsAPI {

    // Chci co? Od API.. JSON?
    @Headers("Content-Type: application/json")
    // metoda
    @GET("pet/findByStatus")
    // Když chci, aby to bylo API, musí to jet na pozadí, takže.. suspend
    // CO se mi vráti z retrofitu 2? Response
    // Bude to padat, neřekne nám, co tu chybí - suspend!!
    suspend fun pets(@Query("status") status: String): Response<List<Pet>>

    // Data do DetailScreeny -> Dokumentace - API - REPOSITORY - VIEW MODEL - UI !!!! tyto classy
    @Headers("Content-Type: application/json")
    @GET("pet/{id}")
    // cesta a ne parametr? Propíše se jako cesta...
    suspend fun pet(@Path("id") id: Long): Response<Pet>

    @Headers("Content-Type: application/json")
    @GET("pet/{id}")
    suspend fun petPhoto(@Path("photoUrls") photoUrls: String): Response<List<Pet>>

    @Headers("Content-Type: application/json")
    @DELETE("pet/{id}")
    suspend fun petDelete(@Path("id") id: Long): Response<Pet>
}