package cz.mendelu.pef.compose.petstore.di

import cz.mendelu.pef.compose.petstore.communication.PetsAPI
import cz.mendelu.pef.compose.petstore.communication.PetsRemoteRepositoryImpl
import org.koin.dsl.module

val remoteRepositoryModule = module {
    single { providePetsRemoteRepository(get()) }
}

fun providePetsRemoteRepository(petsAPI: PetsAPI): PetsRemoteRepositoryImpl
        = PetsRemoteRepositoryImpl(petsAPI)

