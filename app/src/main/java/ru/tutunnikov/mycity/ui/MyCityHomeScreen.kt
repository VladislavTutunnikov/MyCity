package ru.tutunnikov.mycity.ui

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.tutunnikov.mycity.R
import ru.tutunnikov.mycity.data.Place
import ru.tutunnikov.mycity.data.PlaceType
import ru.tutunnikov.mycity.ui.utils.ContentType
import ru.tutunnikov.mycity.ui.utils.NavigationType

@Composable
fun HomeScreen(
    navigationType: NavigationType,
    contentType: ContentType,
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

    if (navigationType == NavigationType.NAVIGATION_RAIL) {
        HomeScreenContent(
            navigationType = navigationType,
            contentType = contentType,
            uiState = uiState,
            navItemsList = navItemsList,
            onTabPressed = onTabPressed,
            onPlaceCardPressed = onPlaceCardPressed,
        )
    } else {
        if (uiState.isShowingHomeScreen) {
            HomeScreenContent(
                navigationType = navigationType,
                contentType = contentType,
                uiState = uiState,
                navItemsList = navItemsList,
                onTabPressed = onTabPressed,
                onPlaceCardPressed = onPlaceCardPressed,
            )
        } else {
            DetailsScreen(
                place = uiState.currentSelectedPlace,
                onDetailScreenBackPressed = onDetailScreenBackPressed,
                isFullScreen = true
            )
        }
    }
}

@Composable
fun HomeScreenContent(
    navigationType: NavigationType,
    contentType: ContentType,
    uiState: MyCityUiState,
    navItemsList: List<NavigationItemContent>,
    onTabPressed: (PlaceType) -> Unit,
    onPlaceCardPressed: (Place) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxSize()
    ) {
        AnimatedVisibility(
            visible = navigationType == NavigationType.NAVIGATION_RAIL
        ) {
            MyCityNavigationRail(
                currentTab = uiState.currentPlaceType,
                onTabPressed = onTabPressed,
                navItemsList = navItemsList,
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.inverseOnSurface)
        ) {
            if (contentType == ContentType.LIST_AND_DETAIL) {
                ListAndDetail(
                    uiState = uiState,
                    onPlaceCardPressed = onPlaceCardPressed,
                    modifier = Modifier.weight(1f)
                )
            } else {
                ListOnlyContent(
                    uiState = uiState,
                    onPlaceCardPressed = onPlaceCardPressed,
                    modifier = Modifier.weight(1f)
                )
            }
            AnimatedVisibility(
                visible = navigationType == NavigationType.BOTTOM_NAVIGATION
            ) {
                BottomNavigationBar(
                    currentTab = uiState.currentPlaceType,
                    onTabPressed = onTabPressed,
                    navItemsList = navItemsList,
                )
            }
        }
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

@Composable
fun MyCityNavigationRail(
    currentTab: PlaceType,
    onTabPressed: (PlaceType) -> Unit,
    navItemsList: List<NavigationItemContent>,
    modifier: Modifier = Modifier
) {
    NavigationRail(
        modifier = modifier
    ) {
        Spacer(Modifier.weight(1f))
        for (navItem in navItemsList) {
            NavigationRailItem(
                selected = currentTab == navItem.placeType,
                onClick = { onTabPressed(navItem.placeType) },
                icon = {
                    Image(
                        painter = painterResource(navItem.icon),
                        contentDescription = null
                    )
                },
                modifier = Modifier.padding(vertical = 16.dp)
            )
        }
        Spacer(Modifier.weight(1f))
    }
}

data class NavigationItemContent(
    val placeType: PlaceType,
    @DrawableRes val icon: Int
)
