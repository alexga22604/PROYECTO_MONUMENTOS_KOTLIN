package com.example.monumentos_practica6

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.monumentos_practica6.ui.MonumentApp
import com.example.monumentos_practica6.ui.theme.Monumentospractica6Theme

class NavActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Monumentospractica6Theme {
                MonumentApp()
            }
        }
    }
}