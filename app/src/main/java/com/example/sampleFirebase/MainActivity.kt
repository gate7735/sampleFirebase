package com.example.sampleFirebase

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sampleFirebase.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding

    private val mainViewModel: MainViewModel by lazy {
        ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                    return MainViewModel() as T
                } else throw IllegalArgumentException()
            }
        }).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(binding.root)
        initViewModel()
    }


    private fun initViewModel() {
        binding.viewModel = mainViewModel

        mainViewModel.mainViewStateLiveData.observe(this) { viewState ->
            when (viewState) {
                is MainViewModel.MainViewState.Join -> {
                    showToast(message = "${viewState.userEmail} 님이 로그인되었습니다.")
                }

                is MainViewModel.MainViewState.LogIn -> {
                    showToast(message = "${viewState.userEmail} 님이 로그인되었습니다.")
                    val intent = Intent(this, BoardListActivity::class.java)
                    startActivity(intent)
                }

                is MainViewModel.MainViewState.Logout -> {
                    showToast(message = "로그아웃되었습니다")
                }

                is MainViewModel.MainViewState.Error -> {
                    showToast(message = viewState.message)
                }

                is MainViewModel.MainViewState.EmptyUserEmail -> {
                    showToast(message = "이메일을 입력하세요.")
                }
                is MainViewModel.MainViewState.EmptyUserPass -> {
                    showToast(message = "비밀번호를 입력하세요.")
                }
            }
        }
    }
}

fun AppCompatActivity.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}