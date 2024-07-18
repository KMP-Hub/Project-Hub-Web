package presentation.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import domain.entities.Project
import domain.repositories.ProjectRepository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: ProjectRepository) : ScreenModel {
    val projects = mutableStateOf(emptyList<Project>())
    val loading = mutableStateOf(false)
    val errorMessage = mutableStateOf<String?>(null)
    val themeSwitched = mutableStateOf(false)

    fun loadProjects() {
        loading.value = true
        screenModelScope.launch {
            repository.fetchProjects().fold(
                onSuccess = {
                    projects.value = it
                    loading.value = false
                },
                onFailure = {
                    errorMessage.value = "Failed to load projects"
                    loading.value = false
                    print(it.message)
                }
            )
        }
    }
}