package com.github.damipen.steamapi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.lifecycle.viewModelScope
import com.github.damipen.steam.Steam
import kotlinx.android.synthetic.main.activity_main.list
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private val key = ""

    private lateinit var viewModel: ActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(ActivityViewModel::class.java)

        viewModel.list.observe(this) {
            list.text = it
        }

        viewModel.getAppList(key)
    }

}

class ActivityViewModel : ViewModel() {

    private val _list: MutableLiveData<String> = MutableLiveData()
    val list: LiveData<String>
        get() = _list

    fun getAppList(key: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                withContext(Dispatchers.IO) {
                    Steam(key).getAppList()
                }
            }.onSuccess { _list.value = it }
                .onFailure { throw it }
        }
    }

}