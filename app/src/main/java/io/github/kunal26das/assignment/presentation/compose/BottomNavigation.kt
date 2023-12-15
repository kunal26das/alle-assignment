package io.github.kunal26das.assignment.presentation.compose

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import io.github.kunal26das.assignment.presentation.Destination
import io.github.kunal26das.assignment.presentation.MainViewModel

@Composable
fun BottomNavigation(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
    navController: NavController,
) {
    val destinations = listOf(Destination.Share, Destination.Info)
    val selectedScreenshot by viewModel.screenshot.collectAsState()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    NavigationBar(
        modifier = modifier,
    ) {
        destinations.forEach { destination ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = when (currentRoute) {
                            destination.route -> destination.selectedIcon
                            else -> destination.unselectedIcon
                        },
                        contentDescription = stringResource(id = destination.label)
                    )
                },
                label = { Text(stringResource(id = destination.label)) },
                selected = currentRoute == destination.route,
                onClick = {
                    if (currentRoute != destination.route) {
                        navController.navigate(destination.route)
                    }
                },
                enabled = when (destination) {
                    Destination.Info -> selectedScreenshot != null
                    else -> true
                }
            )
        }
    }
}