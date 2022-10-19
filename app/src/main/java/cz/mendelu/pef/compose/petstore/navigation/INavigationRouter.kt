package cz.mendelu.pef.compose.petstore.navigation

import androidx.navigation.NavController

interface INavigationRouter {
    fun getNavController(): NavController
    fun returnBack()
    fun navigateToListOfPets()
    fun navigateToPetDetail(id: Long)
    fun returnFromDetailWhenDeleted()

}