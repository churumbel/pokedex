package com.example.pokedeskdiana.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pokedeskdiana.R

@Composable
fun WelcomeScreen(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()){
        Image(
            painter = painterResource(id = R.drawable.poxedesk_portada_01),
            contentDescription = "Fondo de Pokémon",
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize() // Ocupar toda la pantalla
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Button(onClick = { navController.navigate("pokedex") },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Yellow,
                    contentColor = Color.Black)
                ) {
                Text("Ver Pokémons")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { navController.navigate("favorites") },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Yellow,
                    contentColor = Color.Black)
                ) {
                Text("Ver Favoritos")
            }
        }

    }

}
