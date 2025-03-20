package com.example.pokedeskdiana.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.pokedeskdiana.viewmodel.PokemonViewModel
import coil.compose.AsyncImage
import com.example.pokedeskdiana.model.Ability
import com.example.pokedeskdiana.model.AbilityDetail
import com.example.pokedeskdiana.model.Pokemon
import com.example.pokedeskdiana.model.PokemonType
import com.example.pokedeskdiana.model.Sprites
import com.example.pokedeskdiana.model.Stat
import com.example.pokedeskdiana.model.StatDetail
import com.example.pokedeskdiana.model.TypeDetail
import kotlinx.coroutines.flow.MutableStateFlow


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokedexScreen(navController: NavController, viewModel: PokemonViewModel = viewModel()) {
    val context = LocalContext.current
    val pokemon by viewModel.pokemon.collectAsState()
    val isFavorite by viewModel.isFavorite.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    var pokemonName by remember { mutableStateOf("") }

    var showEmptyFieldError by remember { mutableStateOf(false) }

    // Estado para controlar la visibilidad del Snackbar
    val snackbarHostState = remember { SnackbarHostState() }

    // Lanzar el Snackbar cuando showEmptyFieldError sea true
    LaunchedEffect(showEmptyFieldError) {
        if (showEmptyFieldError) {
            snackbarHostState.showSnackbar("El campo de búsqueda no puede estar vacío")
            // Reiniciar el estado después de mostrar el Snackbar
            showEmptyFieldError = false
        }
    }

    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text("Pokedex") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )

        //PokedexTopBar()
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ){ paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                 .background(Color(0xFFB71C1C))
                .padding(16.dp)
                .padding(top = paddingValues.calculateTopPadding()),
            contentAlignment = Alignment.Center

        ){
        Column (horizontalAlignment = Alignment.CenterHorizontally){

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ){
                //bola celeste
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .background(Color.Cyan, CircleShape)
                )
                Spacer(modifier = Modifier.width(18.dp))
                //circulo amarillo
                Box(
                    modifier = Modifier
                        .size(25.dp)
                        .background(Color.Yellow, CircleShape)
                )
                Spacer(modifier = Modifier.width(14.dp))
                //circulo verde
                Box(
                    modifier = Modifier
                        .size(25.dp)
                        .background(Color.Green, CircleShape)
                )

            }
            Spacer(modifier = Modifier.height(16.dp))

            // Pantalla del Pokémon
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .background(Color.White, RoundedCornerShape(16.dp))
                    .border(
                        width = 10.dp, // Grosor del borde
                        color = Color.Gray, // Color del borde
                        shape = RoundedCornerShape(15.dp) // Forma del borde
                    )
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                when {
                    isLoading -> CircularProgressIndicator()
                    error -> Text("Error al cargar el Pokémon", color = Color.Red)
                    pokemon != null -> {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            // Imagen del Pokémon
                            AsyncImage(
                                model = pokemon!!.sprites.front_default,
                                contentDescription = null,
                                modifier = Modifier.size(120.dp)
                            )
                            Spacer(modifier = Modifier.width(16.dp))

                            // Información del Pokémon
                            Column {
                                Text("Nombre: ${pokemon!!.name}", color = Color.Black)
                                Text("Número: #${pokemon!!.id}", color = Color.Black)
                                Text(
                                    "Tipos: ${pokemon!!.types.joinToString { it.type.name }}",
                                    color = Color.Black
                                )
                                // Botón de favoritos
                                Row {
                                    IconButton(onClick = {
                                        viewModel.addToFavorites { wasAdded ->
                                            if (!wasAdded) {
                                                Toast.makeText(context, "Ya está en favoritos", Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                    }) {
                                        Icon(
                                            imageVector = Icons.Default.Star,
                                            contentDescription = "Agregar a favoritos",
                                            tint = if (isFavorite) Color.Yellow else Color.Gray
                                        )
                                    }

                                }



                            }

                        }

                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botones debajo de la imagen del Pokémon
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically

            ){
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .background(Color.Blue, CircleShape)
                )
                Spacer(modifier = Modifier.width(20.dp))
                Button(modifier = Modifier

                    .height(15.dp)//alto
                    .width(70.dp)//largo
                    .border(
                        width = 1.dp, // Grosor del borde
                        color = Color.Black, // Color del borde
                        shape = RoundedCornerShape(15.dp) // Forma del borde
                    )
                    .background(Color.Green, shape = RoundedCornerShape(15.dp)),
                    enabled = false,
                    onClick = {}

                ) { }
                Spacer(modifier = Modifier.width(20.dp))
                Button(modifier = Modifier

                    .height(15.dp)//alto
                    .width(70.dp)//largo
                    .border(
                        width = 1.dp, // Grosor del borde
                        color = Color.Black, // Color del borde
                        shape = RoundedCornerShape(15.dp) // Forma del borde
                    )
                    .background(Color.Yellow, shape = RoundedCornerShape(15.dp)),
                    enabled = false,
                    onClick = {}

                ) { }

                Spacer(modifier = Modifier.width(4.dp))


            }

            Spacer(modifier = Modifier.height(16.dp))

            // Campo de búsqueda y botón
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(Color.Yellow, RoundedCornerShape(16.dp))
                    .padding(5.dp),
                contentAlignment = Alignment.Center
            ) {
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ){
                    TextField(
                        value = pokemonName,
                        onValueChange = { pokemonName = it },
                        label = { Text("Busca tu Pokémon") },
                        modifier = Modifier.weight(0.7f)
                            .height(48.dp)
                            .padding(end=8.dp)
                    )

                    Button(
                        onClick = { if (pokemonName.isBlank()) {
                            // Mostrar error si el campo está vacío
                            showEmptyFieldError = true
                        } else {
                            // Realizar la búsqueda si el campo no está vacío
                            showEmptyFieldError = false
                            viewModel.fetchPokemon(pokemonName)}
                        },
                        modifier = Modifier
                            .weight(0.3f)
                            .height(48.dp)

                            .background(Color.Yellow, RoundedCornerShape(8.dp))
                            .border(
                                width = 1.dp, // Grosor del borde
                                color = Color.Black, // Color del borde
                                shape = RoundedCornerShape(8.dp) // Forma del borde
                            ),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Yellow,
                            contentColor = Color.Black
                        )
                    ){
                        Text("Buscar")
                    }

                }

            }

        }
    }

    }

}


class FakePokemonViewModel : ViewModel() {
    val pokemon = MutableStateFlow(
        Pokemon(
            name = "Pikachu",
            id = 25,
            types = listOf(PokemonType(TypeDetail("Eléctrico"))),
            abilities = listOf(Ability(AbilityDetail("Static"))),
            sprites = Sprites("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/25.png"),
            stats = listOf(Stat(35, StatDetail("hp")))
        )
    )
    val isLoading = MutableStateFlow(false)
    val error = MutableStateFlow(false)
    val isFavorite = MutableStateFlow(false) // Ahora sí lo tenemos
}

/**
@Preview(showBackground = true)
@Composable
fun PokedexScreenPreview() {
    PokedeskDianaTheme {
        val fakeViewModel = FakePokemonViewModel()
        val fakeNavController = rememberNavController() // Simula un NavController
        PokedexScreen(navController = fakeNavController, viewModel = fakeViewModel)
    }
}**/