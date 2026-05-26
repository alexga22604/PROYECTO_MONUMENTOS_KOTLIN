package com.example.monumentos_practica6.ui.my_monument.component
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.monumentos_practica6.data.domain.model.MonumentBo

@Composable
fun MyMonumentItem(
    monument: MonumentBo,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = monument.images.firstOrNull() ?: "",
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(64.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = monument.name,
                fontSize = 18.sp,
                color = Color.Black
            )
            Text(
                text = monument.description,
                fontSize = 14.sp,
                color = Color.Gray,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}