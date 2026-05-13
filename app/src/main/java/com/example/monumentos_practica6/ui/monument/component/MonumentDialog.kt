package com.example.monumentos_practica6.ui.monument.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.monumentos_practica6.R
import com.example.monumentos_practica6.ui.monument.viewmodel.SortType

@Composable
fun SortDialog(
    currentSort: SortType,
    onSortSelected: (SortType) -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = stringResource(id = R.string.sort_by_title)) },
        text = {
            Column {
                SortOptionRow(
                    label = stringResource(id = R.string.sort_date),
                    sortType = SortType.DATE,
                    currentSort = currentSort,
                    onSortSelected = onSortSelected
                )
                SortOptionRow(
                    label = stringResource(id = R.string.sort_name),
                    sortType = SortType.NAME,
                    currentSort = currentSort,
                    onSortSelected = onSortSelected
                )
                SortOptionRow(
                    label = stringResource(id = R.string.sort_north_south),
                    sortType = SortType.NORTH_SOUTH,
                    currentSort = currentSort,
                    onSortSelected = onSortSelected
                )
                SortOptionRow(
                    label = stringResource(id = R.string.sort_east_west),
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
    sortType: SortType,
    currentSort: SortType,
    onSortSelected: (SortType) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSortSelected(sortType) }
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = sortType == currentSort,
            onClick = { onSortSelected(sortType) }
        )
        Text(text = label, modifier = Modifier.padding(start = 8.dp))
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
        title = { Text(text = stringResource(id = R.string.filter_by_country_title)) },
        text = {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onCountrySelected(null) }
                        .padding(vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selectedCountry == null,
                        onClick = { onCountrySelected(null) }
                    )
                    Text(
                        text = stringResource(id = R.string.all_countries),
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }

                countries.forEach { country ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onCountrySelected(country) }
                            .padding(vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedCountry == country,
                            onClick = { onCountrySelected(country) }
                        )
                        Text(text = country, modifier = Modifier.padding(start = 8.dp))
                    }
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