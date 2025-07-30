package com.example.androidapptemplate.ui.detail

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.androidapptemplate.ui.components.FavoritesButton
import com.example.androidapptemplate.ui.components.LoadingIndicator
import com.example.androidapptemplate.ui.navigation.Navigation
import com.example.domain.model.Character
import com.example.domain.model.Episode
import com.example.domain.model.Location

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetailScreen(
    characterId: Int,
    navController: NavController,
    viewModel: CharacterDetailViewModel = hiltViewModel()
) {
    Scaffold(
        contentWindowInsets = WindowInsets.safeDrawing,
        topBar = {
            TopAppBar(
                title = { Text("Favoritos") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        val state by viewModel.uiState.collectAsState()

        // Cargar personaje al entrar
        LaunchedEffect(characterId) {
            viewModel.onEvent(CharacterDetailEvent.LoadCharacter(characterId))
        }

        CharacterDetailScreenContent(
            state = state,
            onBackClick = { navController.popBackStack() },
            onToggleFavorite = {
                state.character?.let {
                    viewModel.onEvent(CharacterDetailEvent.ToggleFavorite(it.id))
                }
            },
            onEpisodeClick = { episodeId ->
                viewModel.onEvent(CharacterDetailEvent.ToggleEpisodeWatched(episodeId))
            },
            onNavigateToFavorites = {
                navController.navigate(Navigation.Favorites.route)
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetailScreenContent(
    state: CharacterDetailUiState,
    onBackClick: () -> Unit = {},
    onToggleFavorite: () -> Unit = {},
    onMapClick: () -> Unit = {},
    onEpisodeClick: (Int) -> Unit = {},
    onNavigateToFavorites: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = state.character?.name ?: "Detalle",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->

        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                LoadingIndicator()
            }
        } else if (state.error != null) {
            Text("Error: ${state.error}", modifier = Modifier.padding(16.dp), color = Color.Red)
        } else if (state.character != null) {
            val character = state.character

            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                // Imagen del personaje
                AsyncImage(
                    model = character.image,
                    contentDescription = character.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(260.dp)
                        .clip(MaterialTheme.shapes.medium)
                        .border(1.dp, Color.LightGray, MaterialTheme.shapes.medium)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Datos generales
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(character.name, style = MaterialTheme.typography.titleLarge)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Género: ${character.gender}")
                        Text("Especie: ${character.species}")
                        Text("Estado: ${character.status}")
                        Text("Ubicación: ${character.location.name}")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Botones de acciones
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = onToggleFavorite,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(if (character.isFavorite) "Quitar favorito" else "Agregar favorito")
                    }

                    FavoritesButton(
                        onClick = onNavigateToFavorites,
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Lista de episodios
                Text("Episodios", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))

                state.episodes.forEachIndexed { index, episode ->
                    Column {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onEpisodeClick(episode.id) }
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("${episode.episodeCode} - ${episode.name}")
                            Spacer(modifier = Modifier.weight(1f))
                            Icon(
                                imageVector = if (episode.isWatched) Icons.Default.Check else Icons.Default.Clear,
                                contentDescription = "Watched"
                            )
                        }
                        if (index < state.episodes.size - 1) {
                            Divider()
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CharacterDetailScreenContentPreview() {
    val mockCharacter = Character(
        id = 1,
        name = "Rick Sanchez",
        species = "Human",
        status = "Alive",
        gender = "Male",
        image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
        location = Location(name = "Earth (C-137)", url = ""),
        episodeIds = listOf(1, 2, 3),
        isFavorite = true
    )

    val mockEpisodes = listOf(
        Episode(id = 1, name = "Pilot", episodeCode = "S01E01", isWatched = true),
        Episode(id = 2, name = "Lawnmower Dog", episodeCode = "S01E02", isWatched = false),
        Episode(id = 3, name = "Anatomy Park", episodeCode = "S01E03", isWatched = true)
    )

    val mockState = CharacterDetailUiState(
        isLoading = false,
        error = null,
        character = mockCharacter,
        episodes = mockEpisodes
    )

    CharacterDetailScreenContent(
        state = mockState,
        onBackClick = {},
        onToggleFavorite = {},
        onMapClick = {},
        onEpisodeClick = {}
    )
}