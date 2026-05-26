package com.example.monumentos_practica6.ui.monument.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.SortByAlpha
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.monumentos_practica6.R
import com.example.monumentos_practica6.ui.monument.viewmodel.SortType
import com.example.monumentos_practica6.ui.theme.DialogBackgroundLight
import com.example.monumentos_practica6.ui.theme.DialogContentColor
import com.example.monumentos_practica6.ui.theme.DialogDividerColor
import com.example.monumentos_practica6.ui.theme.DialogIconTint

@Composable
fun SortDialog(
    currentSort: SortType,
    onSortSelected: (SortType) -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = DialogBackgroundLight,
        title = {
            Text(
                text = stringResource(id = R.string.sort_by_title),
                fontWeight = FontWeight.Bold,
                color = DialogContentColor
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(DialogBackgroundLight)
            ) {
                SortOptionRow(
                    label = stringResource(id = R.string.sort_date),
                    icon = Icons.Default.DateRange,
                    iconDesc = stringResource(id = R.string.icon_date_description),
                    sortType = SortType.DATE,
                    currentSort = currentSort,
                    onSortSelected = onSortSelected
                )
                HorizontalDivider(color = DialogDividerColor)
                SortOptionRow(
                    label = stringResource(id = R.string.sort_name),
                    icon = Icons.Default.SortByAlpha,
                    iconDesc = stringResource(id = R.string.icon_name_description),
                    sortType = SortType.NAME,
                    currentSort = currentSort,
                    onSortSelected = onSortSelected
                )
                HorizontalDivider(color = DialogDividerColor)
                SortOptionRow(
                    label = stringResource(id = R.string.sort_north_south),
                    icon = Icons.Default.ArrowDownward,
                    iconDesc = stringResource(id = R.string.icon_north_south_description),
                    sortType = SortType.NORTH_SOUTH,
                    currentSort = currentSort,
                    onSortSelected = onSortSelected
                )
                HorizontalDivider(color = DialogDividerColor)
                SortOptionRow(
                    label = stringResource(id = R.string.sort_east_west),
                    icon = Icons.Default.ArrowBack,
                    iconDesc = stringResource(id = R.string.icon_east_west_description),
                    sortType = SortType.EAST_WEST,
                    currentSort = currentSort,
                    onSortSelected = onSortSelected
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(id = R.string.close_button))
            }
        }
    )
}

@Composable
private fun SortOptionRow(
    label: String,
    icon: ImageVector,
    iconDesc: String,
    sortType: SortType,
    currentSort: SortType,
    onSortSelected: (SortType) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSortSelected(sortType) }
            .padding(vertical = 12.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = iconDesc,
            tint = DialogIconTint,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = label,
            modifier = Modifier
                .padding(start = 16.dp)
                .weight(1f),
            color = DialogContentColor,
            style = MaterialTheme.typography.bodyLarge
        )
        RadioButton(
            selected = sortType == currentSort,
            onClick = { onSortSelected(sortType) }
        )
    }
}

@Composable
fun FilterDialog(
    countries: List<String>,
    selectedCountry: String?,
    onCountrySelected: (String?) -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = DialogBackgroundLight,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Public,
                    contentDescription = stringResource(id = R.string.icon_country_description),
                    tint = DialogIconTint,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(
                    text = stringResource(id = R.string.filter_by_country_title),
                    fontWeight = FontWeight.Bold,
                    color = DialogContentColor
                )
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(DialogBackgroundLight)
            ) {
                CountryOptionRow(
                    label = stringResource(id = R.string.all_countries),
                    isSelected = selectedCountry == null,
                    onClick = { onCountrySelected(null) }
                )

                countries.forEach { country ->
                    HorizontalDivider(color = DialogDividerColor)
                    CountryOptionRow(
                        label = country,
                        isSelected = selectedCountry == country,
                        onClick = { onCountrySelected(country) }
                    )
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(id = R.string.close_button))
            }
        }
    )
}

@Composable
private fun CountryOptionRow(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 12.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.LocationOn,
            contentDescription = null,
            tint = DialogIconTint,
            modifier = Modifier.size(20.dp)
        )
        Text(
            text = label,
            modifier = Modifier
                .padding(start = 16.dp)
                .weight(1f),
            color = DialogContentColor,
            style = MaterialTheme.typography.bodyLarge
        )
        RadioButton(
            selected = isSelected,
            onClick = onClick
        )
    }
}