package ru.tutunnikov.mycity.ui

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.tutunnikov.mycity.data.local.LocalPlacesDataProvider
import ru.tutunnikov.mycity.ui.theme.MyCityTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    MyCityContent()
}

@Preview(showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    MyCityTheme {
        Surface {
            HomeScreen()
        }
    }
}