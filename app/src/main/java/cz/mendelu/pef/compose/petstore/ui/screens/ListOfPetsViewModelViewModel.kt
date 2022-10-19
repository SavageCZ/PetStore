package cz.mendelu.pef.compose.petstore.ui.screens

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cz.mendelu.pef.compose.petstore.R
import cz.mendelu.pef.compose.petstore.architecture.BaseViewModel
import cz.mendelu.pef.compose.petstore.architecture.CommunicationResult
import cz.mendelu.pef.compose.petstore.communication.PetsRemoteRepositoryImpl
import cz.mendelu.pef.compose.petstore.models.Pet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListOfPetsViewModelViewModel(private val petsRepository: PetsRemoteRepositoryImpl) : BaseViewModel() {

    // Odpověď té obrazovky, té UI z API
    val petsUIState: MutableState<ListOfPetsUiState<List<Pet>>> =
        mutableStateOf(ListOfPetsUiState.Start())

    fun loadPets(){
        launch {  // Pošlu to na pozadí
            val result = withContext(Dispatchers.IO) {
                petsRepository.pets("pending")
            }

            when(result){
                is CommunicationResult.Error -> {
                    // musíme to mít lokalizovaný, nemůžeme tam dát string, ale int

                    petsUIState.value = ListOfPetsUiState.Error(R.string.failed)
                }
                is CommunicationResult.Exception -> {
                    petsUIState.value = ListOfPetsUiState.Error(R.string.no_internet_connection)
                }
                is CommunicationResult.Success -> {
                    petsUIState.value = ListOfPetsUiState.Success(result.data)
                }
            }
        }
    }

}