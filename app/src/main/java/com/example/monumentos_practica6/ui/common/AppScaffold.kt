package com.example.monumentos_practica6.ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.monumentos_practica6.R
import com.example.monumentos_practica6.ui.navigation.ScreenNav
import com.example.monumentos_practica6.ui.theme.DrawerBackground
import com.example.monumentos_practica6.ui.theme.DrawerSelectedBackground
import kotlinx.coroutines.launch

@Composable
fun AppScaffold(
    showNavigationElements: Boolean,
    currentRoute: ScreenNav?,
    onNavigate: (ScreenNav) -> Unit,
    content: @Composable (PaddingValues, () -> Unit) -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val openDrawer: () -> Unit = {
        scope.launch { drawerState.open() }
    }
    val closeDrawer: () -> Unit = {
        scope.launch { drawerState.close() }
    }

    if (showNavigationElements) {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet(
                    drawerContainerColor = DrawerBackground,
                    drawerContentColor = Color.White
                ) {
                    Column(modifier = Modifier.padding(24.dp)) {
                        Text(
                            text = stringResource(id = R.string.app_name),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = stringResource(id = R.string.drawer_subtitle),
                            fontSize = 14.sp,
                            color = Color.LightGray
                        )
                    }

                    HorizontalDivider(color = Color.Gray)
                    Spacer(modifier = Modifier.height(16.dp))

                    DrawerItem(
                        label = stringResource(id = R.string.monuments_title),
                        selected = currentRoute is ScreenNav.Monuments
                    ) {
                        onNavigate(ScreenNav.Monuments)
                        closeDrawer()
                    }
                    DrawerItem(
                        label = stringResource(id = R.string.favorites_title),
                        selected = currentRoute is ScreenNav.Favorites
                    ) {
                        onNavigate(ScreenNav.Favorites)
                        closeDrawer()
                    }
                    DrawerItem(
                        label = stringResource(id = R.string.my_monuments_title),
                        selected = currentRoute is ScreenNav.MyMonuments
                    ) {
                        onNavigate(ScreenNav.MyMonuments)
                        closeDrawer()
                    }
                }
            }
        ) {
            Scaffold { innerPadding ->
                content(innerPadding, openDrawer)
            }
        }
    } else {
        Scaffold { innerPadding ->
            content(innerPadding) {}
        }
    }
}

@Composable
private fun DrawerItem(label: String, selected: Boolean, onClick: () -> Unit) {
    NavigationDrawerItem(
        label = { Text(text = label, color = Color.White) },
        selected = selected,
        onClick = onClick,
        colors = NavigationDrawerItemDefaults.colors(
            unselectedContainerColor = Color.Transparent,
            selectedContainerColor = DrawerSelectedBackground
        ),
        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
    )
}