package com.djordjemancic.piglet.ui.authentication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.djordjemancic.piglet.data.Repository
import com.djordjemancic.piglet.data.dto.PigletUser
import com.djordjemancic.piglet.data.dto.UserData
import com.djordjemancic.piglet.other.Resource

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val _userState: MutableStateFlow<Resource> = MutableStateFlow(Resource(status=Resource.Status.INITIAL))
    val userState get() = _userState

    fun signInUser(email: String, password: String) {
        viewModelScope.launch {
            repository.signInUser(email, password).collect {
                _userState.value = it
            }
        }
    }

    fun signUpUser(firstName: String, lastName: String, email: String, password: String) {
        viewModelScope.launch {
            val user = PigletUser(userData = UserData(firstName = firstName, lastName = lastName, email = email))
            repository.signUpUser(user, password).collect {
                _userState.value = it
            }
        }
    }
}