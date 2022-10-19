package cz.mendelu.pef.compose.petstore.architecture

import retrofit2.Response

interface IBaseRemoteRepository {
    // Zpracovává jakékoli data
    fun <T: Any> processResponse(call: Response<T>): CommunicationResult<T> {
        try {
            if (call.isSuccessful) {
                call.body()?.let {
                    return CommunicationResult.Success(it)
                } ?: kotlin.run {
                    return CommunicationResult.Error(
                        CommunicationError(
                            call.code(),
                            call.errorBody().toString()
                        )
                    )
                }
            } else {
                return CommunicationResult.Error(
                    CommunicationError(
                        call.code(),
                        call.errorBody().toString()
                    )
                )
            }

        } catch (ex: Exception) {
            return CommunicationResult.Exception(ex)
        }
    }
}