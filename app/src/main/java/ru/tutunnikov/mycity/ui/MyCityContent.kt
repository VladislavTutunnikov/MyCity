package ru.tutunnikov.mycity.ui

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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.tutunnikov.mycity.data.Place
import ru.tutunnikov.mycity.data.local.LocalPlacesDataProvider
import ru.tutunnikov.mycity.ui.theme.MyCityTheme
import ru.tutunnikov.mycity.ui.theme.Typography

@Composable
fun MyCityContent(
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier) {
        items(LocalPlacesDataProvider.listOfPlaces) { place ->
            DetailCard(
                place = place,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    }
}

@Composable
fun DetailCard(
    place: Place,
    modifier: Modifier = Modifier,
) {
    Card(modifier = modifier.fillMaxWidth()) {
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
                //.clip(MaterialTheme.shapes.medium)
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


@Preview(showSystemUi = true)
@Composable
fun DetailCardPrevierw() {
    DetailCard(LocalPlacesDataProvider.listOfPlaces[10])
}

