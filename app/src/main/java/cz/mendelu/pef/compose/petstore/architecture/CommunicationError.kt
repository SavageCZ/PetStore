package cz.mendelu.pef.compose.petstore.architecture

// Toto si můžeme následně rozšířit na cokoliv chceme

data class CommunicationError(
    val code: Int,
    var message: String?,
    var secondaryMessage: String? = null)