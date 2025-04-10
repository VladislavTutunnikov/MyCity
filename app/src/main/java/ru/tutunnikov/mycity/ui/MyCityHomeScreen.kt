package ru.tutunnikov.mycity.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.tutunnikov.mycity.R
import ru.tutunnikov.mycity.data.Place
import ru.tutunnikov.mycity.data.PlaceType

@Composable
fun HomeScreen(
    uiState: MyCityUiState,
    onTabPressed: (PlaceType) -> Unit,
    onPlaceCardPressed: (Place) -> Unit,
    onDetailScreenBackPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    val navItemsList = listOf(
        NavigationItemContent(
            placeType = PlaceType.Food,
            icon = R.drawable.ic_food
        ),
        NavigationItemContent(
            placeType = PlaceType.Coffee,
            icon = R.drawable.ic_coffee
        ),
        NavigationItemContent(
            placeType = PlaceType.Mall,
            icon = R.drawable.ic_mall
        ),
        NavigationItemContent(
            placeType = PlaceType.Gym,
            icon = R.drawable.ic_gym
        )
    )
    if(uiState.isShowingHomeScreen) {
        HomeScreenContent(
            uiState = uiState,
            navItemsList = navItemsList,
            onTabPressed = onTabPressed,
            onPlaceCardPressed = onPlaceCardPressed,
        )
    } else {
        DetailsScreen(
            place = uiState.currentSelectedPlace,
            onDetailScreenBackPressed = onDetailScreenBackPressed
        )
    }

}

@Composable
fun HomeScreenContent(
    uiState: MyCityUiState,
    navItemsList: List<NavigationItemContent>,
    onTabPressed: (PlaceType) -> Unit,
    onPlaceCardPressed: (Place) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.inverseOnSurface)
    ) {
        ListOnlyContent(
            uiState = uiState,
            onPlaceCardPressed = onPlaceCardPressed,
            modifier = Modifier.weight(1f)
        )
        BottomNavigationBar(
            currentTab = uiState.currentPlaceType,
            onTabPressed = onTabPressed,
            navItemsList = navItemsList,
        )
    }
}

@Composable
fun BottomNavigationBar(
    currentTab: PlaceType,
    onTabPressed: (PlaceType) -> Unit,
    navItemsList: List<NavigationItemContent>,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier,
    ) {
        for (navItem in navItemsList) {
            NavigationBarItem(
                label = null,
                alwaysShowLabel = false,
                selected = currentTab == navItem.placeType,
                onClick = { onTabPressed(navItem.placeType) },
                icon = {
                    Image(
                        painter = painterResource(navItem.icon),
                        contentDescription = null
                    )
                },
            )
        }
    }
}

data class NavigationItemContent(
    val placeType: PlaceType,
    @DrawableRes val icon: Int
)
