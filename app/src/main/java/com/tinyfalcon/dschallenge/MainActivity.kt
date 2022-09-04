package com.tinyfalcon.dschallenge

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.tinyfalcon.dschallenge.feature.home.presentation.MyTheme
import com.tinyfalcon.dschallenge.feature.home.presentation.SessionsScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                Box(modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black)
                ) {
                    SessionsScreen()
                }
            }
        }
    }
}