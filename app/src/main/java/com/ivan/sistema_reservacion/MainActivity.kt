package com.ivan.sistema_reservacion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ivan.sistema_reservacion.ui.booking.BookingScreen
import com.ivan.sistema_reservacion.ui.home.HomeScreen
import com.ivan.sistema_reservacion.ui.login.LoginScreen
import com.ivan.sistema_reservacion.ui.register.RegisterScreen
import com.ivan.sistema_reservacion.ui.theme.Sistema_ReservacionTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Sistema_ReservacionTheme {
                AppEntryPoint()
            }
        }
    }
}

@Composable
fun AppEntryPoint() {
    val navController = rememberNavController()

    Surface(modifier = Modifier.fillMaxSize()) {
        NavHost(navController = navController, startDestination = "login") {
            composable("login") { LoginScreen(navController = navController) }
            composable("register") { RegisterScreen(navController = navController) }
            composable("home") { HomeScreen(navController = navController) }
            composable("booking") {
                BookingScreen(onBack = {
                    navController.navigate("home") {
                        popUpTo("home") { inclusive = true }
                    }
                })
            }
        }
    }
}
