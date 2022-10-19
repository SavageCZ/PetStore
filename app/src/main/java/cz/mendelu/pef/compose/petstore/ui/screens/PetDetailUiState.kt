package cz.mendelu.pef.compose.petstore.ui.screens

import cz.mendelu.pef.compose.petstore.models.Pet

sealed class PetDetailUiState<out T> {
    class Start : PetDetailUiState<Nothing>()
    class Success<T>(var data: T) : PetDetailUiState<T>()
    class Error(var error: Int) : PetDetailUiState<Nothing>()
    class PetDeleted : PetDetailUiState<Nothing>()
}
