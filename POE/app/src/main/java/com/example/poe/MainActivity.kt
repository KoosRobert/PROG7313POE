package com.example.poe

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import com.example.poe.navigation.AppNavigation
import com.example.poe.ui.theme.POETheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContent {

            val sharedPreferences =
                getSharedPreferences(
                    "theme_prefs",
                    Context.MODE_PRIVATE
                )

            var darkMode by remember {

                mutableStateOf(

                    sharedPreferences.getBoolean(
                        "dark_mode",
                        false
                    )
                )
            }

            POETheme(
                darkTheme = darkMode
            ) {

                AppNavigation(

                    darkMode = darkMode,

                    onThemeToggle = {

                        darkMode = !darkMode

                        sharedPreferences.edit()

                            .putBoolean(
                                "dark_mode",
                                darkMode
                            )

                            .apply()
                    }
                )
            }
        }
    }
}