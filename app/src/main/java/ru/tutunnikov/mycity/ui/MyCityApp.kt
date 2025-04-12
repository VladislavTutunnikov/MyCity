package ru.tutunnikov.mycity.ui

import MyCityTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.tutunnikov.mycity.data.Place
import ru.tutunnikov.mycity.data.PlaceType
import ru.tutunnikov.mycity.ui.utils.ContentType
import ru.tutunnikov.mycity.ui.utils.NavigationType

@Composable
fun MyCityApp(
    windowSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier
) {
    val viewModel: MyCityViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()
    val contentType: ContentType
    val navigationType: NavigationType

    when(windowSize) {
        WindowWidthSizeClass.Compact -> {
            navigationType = NavigationType.BOTTOM_NAVIGATION
            contentType = ContentType.LIST_ONLY
        }
        WindowWidthSizeClass.Medium -> {
            navigationType = NavigationType.BOTTOM_NAVIGATION
            contentType = ContentType.LIST_ONLY
        }
        WindowWidthSizeClass.Expanded -> {
            navigationType = NavigationType.NAVIGATION_RAIL
            contentType = ContentType.LIST_AND_DETAIL
        }
        else -> {
            navigationType = NavigationType.BOTTOM_NAVIGATION
            contentType = ContentType.LIST_ONLY
        }
    }

    HomeScreen(
        navigationType = navigationType,
        contentType = contentType,
        uiState = uiState,
        onTabPressed = {placeType: PlaceType ->
            viewModel.updateCurrentPlaceType(placeType)
            viewModel.resetHomeScreen()
        },
        onPlaceCardPressed = {place: Place ->
            viewModel.updateDetailsScreen(place)
        },
        onDetailScreenBackPressed = {
            viewModel.resetHomeScreen()
        },
        modifier = modifier
    )
}

@Preview(showSystemUi = true)
@Composable
fun MyCityAppPreview() {
    MyCityTheme {
        Surface {
            MyCityApp(
                windowSize = WindowWidthSizeClass.Compact
            )
        }
    }
}