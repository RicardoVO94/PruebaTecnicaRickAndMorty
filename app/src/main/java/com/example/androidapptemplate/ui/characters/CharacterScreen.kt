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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.androidapptemplate.ui.characters.CharacterEvent
import com.example.androidapptemplate.ui.characters.CharacterUiState
import com.example.androidapptemplate.ui.characters.CharacterViewModel
import com.example.androidapptemplate.ui.components.CharacterCard
import com.example.androidapptemplate.ui.components.DropdownMenuFilter
import com.example.androidapptemplate.ui.components.FavoritesButton
import com.example.androidapptemplate.ui.components.LoadingIndicator
import com.example.androidapptemplate.ui.components.SearchBar
import com.example.androidapptemplate.ui.navigation.Navigation
import com.example.domain.model.Character
import com.example.domain.model.Location

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterListScreen(
    viewModel: CharacterViewModel = hiltViewModel(),
    navController: NavController
) {
    Scaffold(
        contentWindowInsets = WindowInsets.safeDrawing
    ) { padding ->
        val state by viewModel.uiState.collectAsState()

        CharacterListScreenContent(
            state = state,
            onEvent = viewModel::onEvent,
            onCharacterClick = { characterId ->
                navController.navigate(Navigation.CharacterDetail.createRoute(characterId))
            },
            onFavoritesClick = {
                navController.navigate(Navigation.Favorites.route)
            }
        )
    }
}

@Composable
fun CharacterListScreenContent(
    state: CharacterUiState,
    onEvent: (CharacterEvent) -> Unit,
    onCharacterClick: (Int) -> Unit,
    onFavoritesClick: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Box() {
            SearchBar(
                query = state.query,
                onQueryChanged = { query -> onEvent(CharacterEvent.OnQueryChanged(query)) },
                onSearch = { onEvent(CharacterEvent.Search) }
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, start = 8.dp, end = 8.dp, bottom = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            DropdownMenuFilter(
                label = "Estado",
                options = listOf("Alive", "Dead", "Unknown"),
                selected = state.status ?: "",
                onSelected = { onEvent(CharacterEvent.OnStatusChanged(it)) }
            )

            DropdownMenuFilter(
                label = "Especie",
                options = listOf("Human", "Alien", "Robot"),
                selected = state.species ?: "",
                onSelected = { onEvent(CharacterEvent.OnSpeciesChanged(it)) }
            )

            FavoritesButton(
                onClick = onFavoritesClick
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, start = 8.dp, end = 8.dp, bottom = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            when {
                state.isLoading -> {
                    Box (modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        LoadingIndicator()
                    }
                }

                state.error != null -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = "Error: ${state.error}", color = Color.Red)
                    }
                }

                state.characters.isEmpty() -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("No hay personajes.")
                    }
                }

                else -> {
                    LazyColumn {
                        items(state.characters) { character ->
                            CharacterCard(
                                character = character,
                                onClick = { onCharacterClick(character.id) },
                                onFavoriteClick = {
                                    onEvent(CharacterEvent.ToggleFavorite(character.id))
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CharacterListScreenPreview() {
    val mockCharacters = listOf(
        Character(
            id = 1,
            name = "Rick Sanchez",
            species = "Human",
            status = "Alive",
            gender = "Male",
            image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
            location = Location("Earth (C-137)", ""),
            episodeIds = listOf(1),
            isFavorite = true
        )
    )

    val state = CharacterUiState(
        characters = mockCharacters,
        query = "Rick",
        status = "Alive",
        species = "Human",
        isLoading = false,
        error = null
    )

    CharacterListScreenContent(
        state = state,
        onEvent = {},
        onCharacterClick = {},
        onFavoritesClick = {}
    )

}