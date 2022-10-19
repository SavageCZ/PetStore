package cz.mendelu.pef.compose.petstore.di

import cz.mendelu.pef.compose.petstore.ui.screens.ListOfPetsViewModelViewModel
import cz.mendelu.pef.compose.petstore.ui.screens.PetDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        ListOfPetsViewModelViewModel(get())
    }

    viewModel {
        PetDetailViewModel(get())
    }
}