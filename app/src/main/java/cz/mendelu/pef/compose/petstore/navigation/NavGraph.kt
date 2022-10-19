package cz.mendelu.pef.compose.petstore.navigation
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import cz.mendelu.pef.compose.petstore.constants.Constants
import cz.mendelu.pef.compose.petstore.ui.screens.ListOfPetsScreen
import cz.mendelu.pef.compose.petstore.ui.screens.PetDetailScreen

@ExperimentalFoundationApi
@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    navigation: INavigationRouter = remember { NavigationRouterImpl(navController) },
    startDestination: String
) {

    NavHost(
        navController = navController,
        startDestination = startDestination){

        composable(Destination.ListOfPetsScreen.route) {
            ListOfPetsScreen(navigation = navigation)
        }

        composable(Destination.PetDetailScreen.route + "/{id}",
            arguments = listOf(
                navArgument(Constants.ID) {
                    type = NavType.LongType
                    defaultValue = -1L
                }
            )
        ) {
            val id = it.arguments?.getLong(Constants.ID)
            PetDetailScreen(navigation = navigation,  id = id!!)
        }
    }
}
