package cz.mendelu.pef.compose.petstore.navigation

import androidx.navigation.NavController
import cz.mendelu.pef.compose.petstore.constants.Constants


/**
 * Implementace navigace.
 */
class NavigationRouterImpl(private val navController: NavController) : INavigationRouter {
    override fun getNavController(): NavController = navController

    override fun returnBack() {
        navController.popBackStack()
    }

    override fun navigateToListOfPets() {
        navController.navigate(Destination.ListOfPetsScreen.route)
    }

    override fun navigateToPetDetail(id: Long) {
        navController.navigate(Destination.PetDetailScreen.route + "/" + id)
    }

    override fun returnFromDetailWhenDeleted() {
        navController.previousBackStackEntry
            ?.savedStateHandle
            ?.set(Constants.REFRESH_SCREEN, true)
        navController.popBackStack()
    }

}