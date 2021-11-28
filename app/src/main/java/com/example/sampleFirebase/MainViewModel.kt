package com.example.sampleFirebase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainViewModel : ViewModel() {

    // Initialize Firebase Auth
    private val auth: FirebaseAuth = Firebase.auth

    val inputUserIdEditText = MutableLiveData<String>()
    val inputUserPassEditText = MutableLiveData<String>()

    private val _mainViewStateLiveData = MutableLiveData<MainViewState>()
    val mainViewStateLiveData: LiveData<MainViewState> = _mainViewStateLiveData

    fun join() {
        checkInput()?.let { user ->
            auth.createUserWithEmailAndPassword(
                user.email,
                user.pass
            ).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onViewChanged(MainViewState.Join(user.email))
                } else {
                    onViewChanged(MainViewState.Error("회원가입이 실패하였습니다"))
                }
            }
        }
    }

    fun logout() {
        auth.signOut()
        onViewChanged(MainViewState.Logout)
    }


    fun login() {
        checkInput()?.let { user ->
            auth.signInWithEmailAndPassword(
                user.email,
                user.pass
            ).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onViewChanged(MainViewState.LogIn(userEmail = user.email))
                } else {
                    onViewChanged(MainViewState.Error("로그인이 실패하였습니다."))
                }
            }
        }
    }

    private fun checkInput(): User? {
        return when {
            inputUserIdEditText.value.isNullOrEmpty() -> {
                onViewChanged(MainViewState.EmptyUserEmail)
                null
            }
            inputUserPassEditText.value.isNullOrEmpty() -> {
                onViewChanged(MainViewState.EmptyUserPass)
                null
            }
            else -> {
                User(inputUserIdEditText.value!!, inputUserPassEditText.value!!)
            }
        }
    }

    private fun onViewChanged(viewState: MainViewState) {
        _mainViewStateLiveData.value = viewState
    }

    private data class User(
        val email: String,
        val pass: String
    )

    sealed class MainViewState {
        data class Join(val userEmail: String) : MainViewState()
        data class LogIn(val userEmail: String) : MainViewState()
        data class Error(val message: String) : MainViewState()
        object EmptyUserEmail : MainViewState()
        object EmptyUserPass : MainViewState()
        object Logout : MainViewState()
    }
}
