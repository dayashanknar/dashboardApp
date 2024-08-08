package com.gamstrain.oppeningapp.repoAndVm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AppDataVm @Inject constructor(private val repository: AppDataRepo) : ViewModel() {

    fun updateAuthToken(token: String) {
        viewModelScope.launch {
            repository.updateAuthToken(token)
        }
    }

    fun getAuthToken(): Flow<String?> {
        return repository.getAuthToken()
    }

//    appDataVm.getAuthToken().collect {
//        actionsVm.deleteComment(it!!, "${feed.postId}_${feed.commentId}")
//        actionMode?.finish() // Finish the action mode after delete action
//        binding.deleteButton.visibility = GONE // Hide delete button after action
//        binding.root.visibility = GONE
//    }

//    appDataVm.updateAuthToken(idToken)
}