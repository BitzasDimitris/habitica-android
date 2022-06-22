package com.habitrpg.wearos.habitica.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.habitrpg.common.habitica.models.responses.TaskDirection
import com.habitrpg.common.habitica.models.responses.TaskScoringResult
import com.habitrpg.common.habitica.models.tasks.TaskType
import com.habitrpg.wearos.habitica.data.repositories.TaskRepository
import com.habitrpg.wearos.habitica.data.repositories.UserRepository
import com.habitrpg.wearos.habitica.models.tasks.Task
import com.habitrpg.wearos.habitica.util.ExceptionHandlerBuilder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val taskRepository: TaskRepository,
    userRepository: UserRepository,
    exceptionBuilder: ExceptionHandlerBuilder
) : BaseViewModel(userRepository, exceptionBuilder) {
    val taskType = TaskType.from(savedStateHandle.get<String>("task_type"))
    val tasks = taskRepository.getTasks(taskType ?: TaskType.HABIT).asLiveData()
    val user =  userRepository.getUser()
        .asLiveData()

    fun scoreTask(task: Task, direction: TaskDirection, onResult: (TaskScoringResult?) -> Unit) {
        viewModelScope.launch(exceptionBuilder.userFacing(this)) {
            val result = taskRepository.scoreTask(user.value, task, direction)
            onResult(result)
        }
    }

}