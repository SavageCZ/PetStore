package cz.mendelu.pef.compose.petstore.communication

import cz.mendelu.pef.compose.petstore.architecture.CommunicationError
import cz.mendelu.pef.compose.petstore.architecture.CommunicationResult
import cz.mendelu.pef.compose.petstore.models.Pet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class PetsRemoteRepositoryImpl(private val petsAPI: PetsAPI) : IPetsRemoteRepository {
    override suspend fun pets(status: String): CommunicationResult<List<Pet>> {
        // Připojení na internet? - try catch stále jak blázni jen kopírujeme, takže pohoda
        try {
            // Odpověď ze serveru...
            // Tohle zavolá Corutinu a vyhodí mi to nějaký výsledek
            // Co zavolám? --- API!
            val response = withContext(Dispatchers.IO){
                petsAPI.pets(status)
            }
            // Mám odpověď a musím si ji zpracovat
            if(response.isSuccessful){
                // Musím zpracovat správnou odpověď
                if(response.body() != null){
                // Pokud je to v pořádku, použiju Succes
                    return CommunicationResult.Success(response.body()!!)
                }else{
                    // CTRL + C
                    return CommunicationResult.Error(
                        // očekává kód a text
                        CommunicationError(response.code(), response.errorBody().toString())
                    )
                }
            }else {
                return CommunicationResult.Error(
                    // očekává kód a text
                    CommunicationError(response.code(), response.errorBody().toString())
                )
            }
        }catch(ex: Exception) {
            return CommunicationResult.Exception(ex)
        }
    }

    override suspend fun pet(id: Long): CommunicationResult<Pet> {
        return try{
            processResponse(withContext(Dispatchers.IO){petsAPI.pet(id)})
        } catch (timeoutEx: SocketTimeoutException) {
            CommunicationResult.Error(CommunicationError(400,"nefunguje"))
            // vše se povede, 300, 400 atd.., nic se mi nevrátí, bude to trvat a neexistuje to
        } catch (unknownHostEx: UnknownHostException){
            // TODO
            CommunicationResult.Error(CommunicationError(500, "nefunguje"))
        }
    }
    override suspend fun petPhoto(photoUrls: String): CommunicationResult<List<Pet>>{
        return try {
            processResponse(withContext(Dispatchers.IO){petsAPI.petPhoto((photoUrls))})
        }catch (timeoutEx: SocketTimeoutException){
            CommunicationResult.Error(CommunicationError(400, "nefunguje"))
        } //TODO
    }

    override suspend fun petDelete(id: Long): CommunicationResult<Pet> {
        return try{
            processResponse(withContext(Dispatchers.IO){petsAPI.petDelete(id)})
        } catch (timeoutEx: SocketTimeoutException) {
            CommunicationResult.Error(CommunicationError(400,"nefunguje"))
            // vše se povede, 300, 400 atd.., nic se mi nevrátí, bude to trvat a neexistuje to
        } catch (unknownHostEx: UnknownHostException){
            // TODO
            CommunicationResult.Error(CommunicationError(500, "nefunguje"))
        }
    }

}