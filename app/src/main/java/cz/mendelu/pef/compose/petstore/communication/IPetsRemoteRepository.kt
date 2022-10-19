package cz.mendelu.pef.compose.petstore.communication

import cz.mendelu.pef.compose.petstore.architecture.CommunicationResult
import cz.mendelu.pef.compose.petstore.architecture.IBaseRemoteRepository
import cz.mendelu.pef.compose.petstore.models.Pet
import retrofit2.Response
import retrofit2.http.*

interface IPetsRemoteRepository : IBaseRemoteRepository{

    // zkopírováním z API smažu Query a Response
    // Je to kvůli architektuře a kvůli Retrofitu, nemusím ho dále užívat
    suspend fun pets(status: String): CommunicationResult<List<Pet>>
    suspend fun petPhoto(photoUrls: String): CommunicationResult<List<Pet>>
    suspend fun pet(id: Long): CommunicationResult<Pet>
    suspend fun petDelete(id: Long): CommunicationResult<Pet>

}