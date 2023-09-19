package com.djordjemancic.piglet.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.djordjemancic.piglet.data.Repository
import com.djordjemancic.piglet.data.dto.Transaction
import com.djordjemancic.piglet.other.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val _userState: MutableStateFlow<Resource> = MutableStateFlow(Resource(status=Resource.Status.INITIAL))
    val userState: MutableStateFlow<Resource> get() = _userState

    init {
        viewModelScope.launch {
               repository.getUser().collect {
                   _userState.value = it
                   println("ViewModel coroutine user updated ${_userState.value}")
               }
        }

    }

//    fun addTransaction() {
//        authentication.addTransaction()
//    }


//   suspend fun createUser(): PigletUserFullEntity {
////        firestore.document(user.auth!!.uid).set(gson.fromJson(gson.toJson(user.copy(tags= listOf(Tag(tagName = "testtag", tagColor = "zelena")))), object : TypeToken<Map<String, Any>>() {}.type)).await()
////      viewModelScope.launch {
////          userDao.upsertUser(user)
//       return  userFullDao.getUserWithData()
////      }
//
////        return user
//    }

    fun addTransaction() {
        viewModelScope.launch {
            repository.addTransaction(Transaction(tag = null, amount = 100.0, title = "test trabns", date= "2002.23.23"))
        }

    }

    fun signOut() {
        repository.signOut()
    }
}