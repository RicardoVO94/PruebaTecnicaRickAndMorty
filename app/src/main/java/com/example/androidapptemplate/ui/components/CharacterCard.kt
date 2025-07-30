package com.example.androidapptemplate.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.domain.model.Character
import com.example.domain.model.Location

@Composable
fun CharacterCard(
    character: Character,
    onClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = character.image,
                contentDescription = character.name,
                modifier = Modifier.size(80.dp)
            )

            Column(modifier = Modifier.weight(1f).padding(8.dp)) {
                Text(text = character.name, fontWeight = FontWeight.Bold)
                Text(text = "Especie: ${character.species}")
                Text(text = "Estado: ${character.status}")
            }

            IconButton(onClick = onFavoriteClick) {
                Icon(
                    imageVector = if (character.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Favorito"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CharacterCardPreview() {
    val character = Character(
        id = 1,
        name = "Rick Sanchez",
        species = "Human",
        status = "Alive",
        gender = "Male",
        image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
        location = Location(
            name = "Earth (C-137)",
            url = "https://rickandmortyapi.com/api/location/1"
        ),
        episodeIds = listOf(1, 2, 3),
        isFavorite = true
    )

    CharacterCard(
        character = character,
        onClick = {},
        onFavoriteClick = {}
    )
}
