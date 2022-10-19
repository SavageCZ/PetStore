package cz.mendelu.pef.compose.petstore.ui.screens

import android.provider.ContactsContract.CommonDataKinds.Photo
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.contentColorFor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import cz.mendelu.pef.compose.petstore.R
import cz.mendelu.pef.compose.petstore.models.Pet
import cz.mendelu.pef.compose.petstore.models.ScreenState
import cz.mendelu.pef.compose.petstore.navigation.INavigationRouter
import cz.mendelu.pef.compose.petstore.ui.elements.BackArrowScreen
import cz.mendelu.pef.compose.petstore.ui.elements.ErrorScreen
import cz.mendelu.pef.compose.petstore.ui.elements.LoadingScreen
import org.koin.androidx.compose.getViewModel
import retrofit2.http.DELETE
import retrofit2.http.Path

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PetDetailScreen(navigation: INavigationRouter,
                    id: Long,
                viewModel: PetDetailViewModel = getViewModel()
) {

    // Tohle mně musí probublat do contantu
    val screenState: MutableState<ScreenState<Pet>> = rememberSaveable{
        mutableStateOf(ScreenState.Loading())
    }


    viewModel.petsUIState.value.let {
        when(it){
            is PetDetailUiState.Error -> {
                screenState.value = ScreenState.Error(it.error)
            }
            is PetDetailUiState.PetDeleted -> {
                LaunchedEffect(it) {
                    viewModel.deletePet(id)
                }
                //TODO
            }
            is PetDetailUiState.Start -> {
                LaunchedEffect(it) {
                    viewModel.loadPet(id)
                }
                // DOkumentace - vím co mám dělat - do API
                // -> na pozadí neustále provoláváme tento request, proto ten Launch effect
            }
            is PetDetailUiState.Success -> {
                screenState.value = ScreenState.DataLoaded(it.data)
            }
        }
    }


    BackArrowScreen(
        topBarText = stringResource(R.string.pet_detail),
        content = {
            PefDetailScreenContent(
                screenState = screenState.value,
                navigation = navigation,
            )
        },
        actions = {
            IconButton(modifier = Modifier.onKeyEvent {
                if (it.key == Key.Delete) {
                        viewModel.deletePet(id)
                    true
                } else {
                    false
                }
            }, onClick ={
                        // TODO, umí to delete, ale nenavrací se zpátky
                navigation.returnFromDetailWhenDeleted()
                viewModel.deletePet(id)
            }) {


                /*
                IconButton(
                    modifier = Modifier.onKeyEvent {
                        if (it.key == Key.Delete) {
                            // Action When Click on Delete
                            viewModel.deletePet(id)
                            true
                        } else {
                            false
                        }
                    }
                )
                */


                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null,
                    tint = Color.Black
                )
            }
        },
        onBackClick = { navigation.returnBack() }
    )

}

@Composable
fun PefDetailScreenContent(
    screenState: ScreenState<Pet>,
    navigation: INavigationRouter,
    ) {

    screenState.let {
        when (it) {

            is ScreenState.DataLoaded -> PetInfo(
                pet = it.data

            )
        }
    }

}

@Composable
fun PetInfo(
    pet:Pet
){
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 16.dp)
            ) {
        Column {
            Text(
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                text = pet.status?:"")

            /*
            Image(
                painter = rememberAsyncImagePainter(model = pet.photoUrls),
                contentDescription = null,
                modifier = Modifier.size(128.dp))
            */
            /*
            Text(
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                text = pet.photoUrls[0]?:"")
            */

            Text(
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                text = pet.name?:"")
        }
        /*
        Column {
            val image = rememberImagePainter(data = pet.photoUrls)
            Image(
                painter = image,
                contentDescription = "image",
                modifier = Modifier.size(10.dp)
            )
        }
        /*
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            pet.photoUrls.forEach {
                item(key = it) {
                    PetRow(
                        pet = String()
                    )
                }
            }
        }
        */
        */
    }

    if(!pet.photoUrls.isNullOrEmpty()) {
        Image(
            painter = rememberAsyncImagePainter(pet.photoUrls[0]),
            contentDescription = null,
            modifier = Modifier.size(128.dp)
        )
        //imageFromURL()
    }
}
@Composable
fun PetRow(pet: String){
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 16.dp)
            ) {

        Column {
            Text(
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                text = pet?:"")
        }
    }
}
@Composable
fun imageFromURL() {
    // on below line we are creating a column,
    Column(
        // in this column we are adding modifier
        // to fill max size, mz height and max width
        modifier = Modifier
            .fillMaxSize()
            .fillMaxHeight()
            .fillMaxWidth()
            // on below line we are adding
            // padding from all sides.
            .padding(10.dp),
        // on below line we are adding vertical
        // and horizontal arrangement.
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // on below line we are adding image for our image view.
        Image(
            // on below line we are adding the image url
            // from which we will  be loading our image.
            painter = rememberAsyncImagePainter("https://media.geeksforgeeks.org/wp-content/uploads/20210101144014/gfglogo.png/"),

            // on below line we are adding content
            // description for our image.
            contentDescription = "gfg image",

            // on below line we are adding modifier for our
            // image as wrap content for height and width.
            modifier = Modifier
                .wrapContentSize()
                .wrapContentHeight()
                .wrapContentWidth()
        )
    }
}

