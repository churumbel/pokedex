package com.example.pokedeskdiana

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.pokedeskdiana.navegation.NavGraph
import com.example.pokedeskdiana.ui.theme.PokedeskDianaTheme
import com.example.pokedeskdiana.screens.PokedexScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokedeskDianaTheme {
                NavGraph()
            }

        }
    }
}
