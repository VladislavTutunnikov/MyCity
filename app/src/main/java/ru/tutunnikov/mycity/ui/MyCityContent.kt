package ru.tutunnikov.mycity.ui

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.tutunnikov.mycity.data.Place
import ru.tutunnikov.mycity.ui.theme.Typography


@SuppressLint("ContextCastToActivity")
@Composable
fun ListAndDetail(
    uiState: MyCityUiState,
    onPlaceCardPressed: (Place) -> Unit,
    modifier: Modifier = Modifier
) {
    val listOfPlaces = uiState.currentPlacesList

    Row(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .weight(1f)
        ) {
            items(listOfPlaces) { place ->
                PlaceCard(
                    place = place,
                    onPlaceCardPressed = { onPlaceCardPressed(place) },
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }
        val activity = LocalContext.current as Activity
        DetailsScreen(
            place = uiState.currentSelectedPlace,
            onDetailScreenBackPressed = { activity.finish() },
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
        )


    }
}

@Composable
fun ListOnlyContent(
    uiState: MyCityUiState,
    onPlaceCardPressed: (Place) -> Unit,
    modifier: Modifier = Modifier
) {
    val listOfPlaces = uiState.currentPlacesList
    LazyColumn(modifier = modifier) {
        items(listOfPlaces) { place ->
            PlaceCard(
                place = place,
                onPlaceCardPressed = { onPlaceCardPressed(place) },
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp)
            )
        }
    }
}

@Composable
fun PlaceCard(
    onPlaceCardPressed: () -> Unit,
    place: Place,
    modifier: Modifier = Modifier,
) {
    Card(
        onClick = onPlaceCardPressed,
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(place.imageId),
                contentDescription = stringResource(place.name),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(120.dp)
                    .width(160.dp)
            )
            Spacer(Modifier.padding(5.dp))
            Column {
                Text(
                    text = stringResource(place.name),
                    style = Typography.bodyLarge
                )
                Spacer(Modifier.padding(2.dp))
                Text(
                    text = stringResource(place.address),
                    style = Typography.bodySmall
                )
            }
        }
    }
}

