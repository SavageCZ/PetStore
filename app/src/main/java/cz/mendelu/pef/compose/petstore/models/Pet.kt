package cz.mendelu.pef.compose.petstore.models

data class Pet(
    var id: Long,
    var category: Category,
    var name: String,
    var photoUrls: List<String>,
    var tags: List<Tag>,
    var status: String)
