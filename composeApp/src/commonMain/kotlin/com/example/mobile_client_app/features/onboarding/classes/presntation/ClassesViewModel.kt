package com.example.mobile_client_app.features.onboarding.classes.presntation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile_client_app.features.onboarding.classes.domain.model.ClassFilter
import com.example.mobile_client_app.features.onboarding.classes.domain.model.FitnessClass
import com.example.mobile_client_app.features.onboarding.classes.domain.usecase.GetClassesUseCase
import com.example.mobile_client_app.util.network.onError
import com.example.mobile_client_app.util.network.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class ClassesScreenUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val classes: List<FitnessClass> = emptyList(),
    val selectedFilter: ClassFilter = ClassFilter.ALL,
)

class ClassesViewModel(
    private val getClassesUseCase: GetClassesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ClassesScreenUiState())
    val uiState: StateFlow<ClassesScreenUiState> = _uiState.asStateFlow()

    var filteredClasses = mutableStateOf<List<FitnessClass>>(emptyList())
        private set

    init {
        getClasses()
    }

    fun refresh() {
        getClasses()
    }

    private fun getClasses() {
        viewModelScope.launch {
            _uiState.value = ClassesScreenUiState(isLoading = true, error = null, classes = emptyList(), selectedFilter = ClassFilter.ALL)
            getClassesUseCase.invoke().onError {
                _uiState.value = ClassesScreenUiState(isLoading = false, error = it.displayMessage)
            }.onSuccess { classList ->
                _uiState.value = ClassesScreenUiState(isLoading = false, classes = classList.map { it.toDto() })
                filteredClasses.value = getFilteredClasses()
            }
        }
    }

    fun selectFilter(filter: ClassFilter) {
        _uiState.value = _uiState.value.copy(selectedFilter = filter)
        filteredClasses.value = getFilteredClasses()
    }

    fun getFilteredClasses(): List<FitnessClass> {
        val currentState = _uiState.value
        return when (currentState.selectedFilter) {
            ClassFilter.ALL -> currentState.classes
            ClassFilter.TODAY -> currentState.classes.filter { it.date == FitnessClass.ClassDate.TODAY }
            ClassFilter.TOMORROW -> currentState.classes.filter { it.date == FitnessClass.ClassDate.TOMORROW }
        }
    }
}

