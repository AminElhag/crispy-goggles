package com.example.mobile_client_app.features.personalTraining.trainerSelection

import com.example.mobile_client_app.features.personalTraining.trainerSelection.data.remote.PersonalTrainerApi
import com.example.mobile_client_app.features.personalTraining.trainerSelection.data.remote.impl.PersonalTrainerApiImpl
import com.example.mobile_client_app.features.personalTraining.trainerSelection.domain.repository.PersonalTrainerRepository
import com.example.mobile_client_app.features.personalTraining.trainerSelection.domain.repository.impl.PersonalTrainerRepositoryImpl
import com.example.mobile_client_app.features.personalTraining.trainerSelection.domain.usecase.GetAvailableTimeUseCase
import com.example.mobile_client_app.features.personalTraining.trainerSelection.domain.usecase.GetTrainersUseCase
import com.example.mobile_client_app.features.personalTraining.trainerSelection.domain.usecase.GetTrainingSpecializationUseCase
import com.example.mobile_client_app.features.personalTraining.trainerSelection.domain.usecase.RequestAppointmentUseCase
import com.example.mobile_client_app.features.personalTraining.trainerSelection.presentation.trainerSelection.TrainerSelectionViewModel
import com.example.mobile_client_app.features.personalTraining.trainerSelection.presentation.trainingSelection.TrainingSelectionViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val trainerSelection = module {
    viewModel { TrainerSelectionViewModel(get()) }
    single { GetTrainersUseCase(get()) }
    single<PersonalTrainerRepository> { PersonalTrainerRepositoryImpl(get()) }
    single<PersonalTrainerApi> { PersonalTrainerApiImpl(get()) }

    viewModel { TrainingSelectionViewModel(get(), get(), get()) }
    single { GetTrainingSpecializationUseCase(get()) }
    single { GetAvailableTimeUseCase(get()) }
    single { RequestAppointmentUseCase(get()) }
}