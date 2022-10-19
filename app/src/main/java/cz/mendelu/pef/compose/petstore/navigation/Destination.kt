package cz.mendelu.pef.compose.petstore.navigation

sealed class Destination(
    val route: String
){
    object ListOfPetsScreen : Destination(route = "list_of_pets")
    object PetDetailScreen : Destination(route = "pet_detail")
}
