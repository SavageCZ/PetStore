package cz.mendelu.pef.compose.petstore.architecture

// výsledek komunikace

sealed class CommunicationResult<out T : Any> {
    class Success<out T : Any>(val data: T) : CommunicationResult<T>()
    class Error(val error: CommunicationError) : CommunicationResult<Nothing>()
    class Exception(val exception: Throwable) : CommunicationResult<Nothing>() // může dojít k chybě při parsování JSONu +
// chyba komunikace - nenapíše chybový kód, ale prostě vyhodí Exception
}