package cz.mendelu.pef.compose.petstore.ui.screens

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import cz.mendelu.pef.compose.petstore.R
import cz.mendelu.pef.compose.petstore.architecture.BaseViewModel
import cz.mendelu.pef.compose.petstore.architecture.CommunicationResult
import cz.mendelu.pef.compose.petstore.communication.PetsRemoteRepositoryImpl
import cz.mendelu.pef.compose.petstore.models.Pet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PetDetailViewModel(private var petsRepository: PetsRemoteRepositoryImpl) : BaseViewModel() {
    // Odpověď té obrazovky, té UI z API
    val petsUIState: MutableState<PetDetailUiState<Pet>> =
        mutableStateOf(PetDetailUiState.Start())

    fun loadPet(id: Long){
        launch {  // Pošlu to na pozadí
            val result = withContext(Dispatchers.IO) {
                petsRepository.pet(id)
            }
            when(result){
                is CommunicationResult.Error -> {
                    // musíme to mít lokalizovaný, nemůžeme tam dát string, ale int

                    petsUIState.value = PetDetailUiState.Error(R.string.failed)
                }
                is CommunicationResult.Exception -> {
                    petsUIState.value = PetDetailUiState.Error(R.string.no_internet_connection)
                }
                is CommunicationResult.Success -> {
                    petsUIState.value = PetDetailUiState.Success(result.data)
                }
            }
        }
    }

    fun deletePet(id: Long){
        launch {
            val result = withContext(Dispatchers.IO) {
                petsRepository.petDelete(id)
            }
            when(result){
                is CommunicationResult.Error -> {
                    // musíme to mít lokalizovaný, nemůžeme tam dát string, ale int

                    petsUIState.value = PetDetailUiState.Error(R.string.failed)
                }
                is CommunicationResult.Exception -> {
                    petsUIState.value = PetDetailUiState.Error(R.string.no_internet_connection)
                }
                is CommunicationResult.Success -> {
                    petsUIState.value = PetDetailUiState.Success(result.data)
                }
            }
        }
    }
}