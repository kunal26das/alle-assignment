package io.github.kunal26das.assignment.presentation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Share
import androidx.compose.ui.graphics.vector.ImageVector
import io.github.kunal26das.assignment.R

sealed class Destination(
    val route: String,
    @StringRes val label: Int,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
) {
    data object Share : Destination(
        route = "share",
        label = R.string.share,
        selectedIcon = Icons.Filled.Share,
        unselectedIcon = Icons.Outlined.Share,
    )

    data object Info : Destination(
        route = "info",
        label = R.string.info,
        selectedIcon = Icons.Filled.Info,
        unselectedIcon = Icons.Outlined.Info,
    )

    data object Delete : Destination(
        route = "delete",
        label = R.string.delete,
        selectedIcon = Icons.Filled.Delete,
        unselectedIcon = Icons.Outlined.Delete,
    )
}