package com.vinayak.mviarchitecturedemo.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.vinayak.mviarchitecturedemo.R
import com.vinayak.mviarchitecturedemo.util.MainContract
import com.vinayak.mviarchitecturedemo.ui.compose.IndeterminateCircularIndicator
import com.vinayak.mviarchitecturedemo.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private var loading by mutableStateOf(false)
    private var textCounter by mutableIntStateOf(0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                Button(onClick = {
                    viewModel.setEvent(MainContract.Event.OnRandomNumberClicked)
                }) {
                    Text(stringResource(R.string.generate_number))
                }

                Button(onClick = {
                    viewModel.setEvent(MainContract.Event.OnShowToastClicked)
                }) {
                    Text(stringResource(R.string.show_toast))
                }

                Button(onClick = {
                    startActivity(Intent(this@MainActivity, SecondActivity::class.java))
                }) {
                    Text("Open Second Activity")
                }
                Spacer(modifier = Modifier.height(23.dp))
                Text(text = stringResource(R.string.generated_number, textCounter))
                Spacer(modifier = Modifier.height(23.dp))
                IndeterminateCircularIndicator(loadingStatus = loading)
            }
        }
        initObservers()
    }

    /**
     * Initialize Observers
     */
    private fun initObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect {
                when (it.randomNumberState) {
                    is MainContract.RandomNumberState.Idle -> {
                        Log.d(TAG, "initObservers: Idle")
                        loading
                    }

                    is MainContract.RandomNumberState.Loading -> {
                        Log.d(TAG, "initObservers: Loading")
                        loading = true
                    }

                    is MainContract.RandomNumberState.Success -> {
                        Log.d(TAG, "initObservers: Success")
                        loading = false
                        textCounter = it.randomNumberState.number
                    }

                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.effect.collect {
                when (it) {
                    is MainContract.Effect.ShowToast -> {
                        loading = false
                        showToast("Error, number is even")
                    }

                }
            }
        }
    }


    /**
     * Show simple toast message
     */
    private fun showToast(message: String) {
        Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
    }

    private companion object {
        private const val TAG = "MainActivity"
    }
}