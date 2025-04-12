package ru.tutunnikov.mycity

import MyCityTheme
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import com.yandex.mapkit.MapKitFactory
import ru.tutunnikov.mycity.ui.MyCityApp

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            Log.e("MyCity", "Crash in thread ${thread.name}", throwable)
            finish()
        }

        enableEdgeToEdge()
        setContent {
            MyCityTheme(darkTheme = false) {
                val layoutDirection = LocalLayoutDirection.current
                Surface(
                    modifier = Modifier
                        .padding(
                            start = WindowInsets.safeDrawing.asPaddingValues()
                                .calculateStartPadding(layoutDirection),
                            end = WindowInsets.safeDrawing.asPaddingValues()
                                .calculateEndPadding(layoutDirection),
                            top = WindowInsets.safeDrawing.asPaddingValues()
                                .calculateTopPadding(),
                        )
                        .fillMaxSize()
                ) {
                    val windowSize = calculateWindowSizeClass(this)
                    MyCityApp(
                        windowSize = windowSize.widthSizeClass,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}





