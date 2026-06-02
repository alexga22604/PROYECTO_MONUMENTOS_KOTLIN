package com.example.monumentos_practica6.ui.monument.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.monumentos_practica6.R
import com.example.monumentos_practica6.data.domain.model.MonumentBo
import com.example.monumentos_practica6.ui.theme.FavoriteStarActive
import com.example.monumentos_practica6.ui.theme.FavoriteStarInactive
import com.example.monumentos_practica6.ui.theme.ItemRowBackground
import com.example.monumentos_practica6.ui.theme.TextPrimaryDark
import com.example.monumentos_practica6.ui.theme.TextSecondaryDark

@Composable
fun MonumentItem(
    monument: MonumentBo,
    onClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RectangleShape
    ) {
        Column {
            if (monument.images.isNotEmpty()) {
                AsyncImage(
                    model = monument.images.first(),
                    contentDescription = monument.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(ItemRowBackground)
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = monument.name,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Medium,
                        color = TextPrimaryDark
                    )

                    Text(
                        text = monument.city,
                        fontSize = 16.sp,
                        color = TextSecondaryDark,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

                IconButton(
                    onClick = onFavoriteClick
                ) {
                    Icon(
                        imageVector = if (monument.isFavorite) Icons.Filled.Star else Icons.Outlined.StarOutline,
                        contentDescription = stringResource(id = R.string.favorite_toggle_description),
                        tint = if (monument.isFavorite) FavoriteStarActive else FavoriteStarInactive,
                        modifier = Modifier.size(36.dp)
                    )
                }
            }
        }
    }
}