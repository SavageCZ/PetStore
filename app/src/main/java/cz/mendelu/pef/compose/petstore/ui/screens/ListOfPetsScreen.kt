package cz.mendelu.pef.compose.petstore.ui.screens

import android.annotation.SuppressLint
import android.provider.ContactsContract
import android.provider.ContactsContract.CommonDataKinds.Photo
import android.widget.Space
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import cz.mendelu.pef.compose.petstore.R
import cz.mendelu.pef.compose.petstore.constants.Constants
import cz.mendelu.pef.compose.petstore.extensions.getValue
import cz.mendelu.pef.compose.petstore.extensions.removeValue
import cz.mendelu.pef.compose.petstore.models.Pet
import cz.mendelu.pef.compose.petstore.models.ScreenState
import cz.mendelu.pef.compose.petstore.navigation.INavigationRouter
import cz.mendelu.pef.compose.petstore.ui.elements.ErrorScreen
import cz.mendelu.pef.compose.petstore.ui.elements.LoadingScreen
import org.koin.androidx.compose.getViewModel
// Jednoduchá obrazovka..
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListOfPetsScreen(navigation: INavigationRouter,
                viewModel: ListOfPetsViewModelViewModel = getViewModel()
) {
    // Tohle mně musí probublat do contantu
    val screenState: MutableState<ScreenState<List<Pet>>> = rememberSaveable{
        mutableStateOf(ScreenState.Loading())
    }

    viewModel.petsUIState.value.let {
        when(it){
            is ListOfPetsUiState.Error -> {
                screenState.value = ScreenState.Error(it.error)
            }
            is ListOfPetsUiState.Start -> {
                /*
                LaunchedEffect(it) {
                    viewModel.loadPets()
                }
                */
                viewModel.loadPets()
                // DOkumentace - vím co mám dělat - do API
            // -> na pozadí neustále provoláváme tento request, proto ten Launch effect
            }
            is ListOfPetsUiState.Success -> {
                screenState.value = ScreenState.DataLoaded(it.data)
                viewModel.loadPets()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(align = Alignment.CenterHorizontally)
                    ) {
                        Text(
                            text = stringResource(id = R.string.app_name),
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Black,
                            modifier = Modifier
                                .padding(start = 0.dp)
                                .weight(1.5f)
                        )
                    }
                },
                elevation = 0.dp,
                backgroundColor = MaterialTheme.colorScheme.background
            )
        },
        content = {
            PetsListScreenContent(
                paddingValues = it,
                navigation = navigation,
                screenState = screenState.value
            )
        },
    )
}

@Composable
fun PetsListScreenContent(
    paddingValues: PaddingValues,
    navigation: INavigationRouter,
    screenState: ScreenState<List<Pet>>){
    // zpracování stavu

    screenState.let {
        when(it){
            is ScreenState.DataLoaded -> PetsList(
                paddingValues = paddingValues,
                navigation = navigation,
                pets = it.data)
            is ScreenState.Error -> ErrorScreen(text = stringResource(id = it.error))
            is ScreenState.Loading -> LoadingScreen()
        }
    }

}

@Composable
fun PetsList(paddingValues: PaddingValues,
             navigation: INavigationRouter,
             pets: List<Pet>){
    // Toto by mělo zobrazit ten seznam
    LazyColumn(modifier = Modifier.padding(paddingValues)) {
        pets.forEach {
            item(key = it.id) {
                PetRow(
                    pet = it,
                    onRowClick = {
                        navigation.navigateToPetDetail(it.id)
                    }
                )
            }
        }
    }
}

@Composable
fun PetRow(pet: Pet,
           onRowClick: () -> Unit){
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 16.dp)
            .clickable(onClick = onRowClick)) {

        Column {
            Text(
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                text = pet.name?:"")
        }
    }
}

