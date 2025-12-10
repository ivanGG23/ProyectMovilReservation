package com.ivan.sistema_reservacion.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ivan.sistema_reservacion.ui.theme.Sistema_ReservacionTheme
import kotlinx.coroutines.launch

// --- Estructura principal de la pantalla Home ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    // Estado para controlar el cajón de navegación (abierto/cerrado)
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    // Coroutine scope para abrir/cerrar el cajón de forma asíncrona
    val scope = rememberCoroutineScope()

    // El NavigationDrawer es el contenedor principal que gestiona el cajón lateral
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            // Contenido del menú lateral
            AppDrawer(
                onProfileClicked = { /* TODO: Navegar a Perfil */ },
                onReservationClicked = {
                    scope.launch {
                        if (!drawerState.isClosed) drawerState.close()
                        navController.navigate("booking")
                    }
                },
                onLogoutClicked = {
                    // Cerrar el drawer y navegar a la pantalla de Login limpiando la pila
                    scope.launch {
                        if (!drawerState.isClosed) drawerState.close()
                        navController.navigate("login") {
                            popUpTo("login") { inclusive = true }
                        }
                    }
                }
            )
        }
    ) {
        // Contenido principal de la pantalla (lo que se ve cuando el menú está cerrado)
        Scaffold(
            topBar = {
                // Barra superior con el ícono de menú y el título
                TopAppBar(
                    title = { Text("ElegantFood", color = Color.White) },
                    navigationIcon = {
                        IconButton(onClick = {
                            // Abre el cajón de navegación
                            scope.launch {
                                drawerState.apply {
                                    if (isClosed) open() else close()
                                }
                            }
                        }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menú", tint = Color.White)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0xFF1A237E) // Un color oscuro para la barra superior
                    )
                )
            }
        ) { paddingValues ->
            // Contenido principal de la pantalla de inicio
            HomeContent(modifier = Modifier.padding(paddingValues))
        }
    }
}

// --- Contenido del menú lateral (Drawer) ---
@Composable
fun AppDrawer(
    onProfileClicked: () -> Unit,
    onReservationClicked: () -> Unit,
    onLogoutClicked: () -> Unit
) {
    ModalDrawerSheet(
        modifier = Modifier.fillMaxHeight(),
        drawerContainerColor = Color(0xFF1E3A8A) // Azul oscuro para el fondo del menú
    ) {
        // Encabezado del menú
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "ElegantFood",
                fontSize = 22.sp,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Opciones de navegación
        DrawerItem(icon = Icons.Default.Person, text = "Perfil", isSelected = true, onClick = onProfileClicked)
        DrawerItem(icon = Icons.Default.DateRange, text = "Reservacion", onClick = onReservationClicked)

        // Espaciador para empujar el botón de "Cerrar Sesión" hacia abajo
        Spacer(modifier = Modifier.weight(1f))

        // Botón de Cerrar Sesión en la parte inferior del Drawer
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp), contentAlignment = Alignment.Center) {
            Button(
                onClick = onLogoutClicked,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB00020))
            ) {
                Text(text = "Cerrar Sesión", color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
    }
}

// --- Componente reutilizable para cada opción del menú ---
@Composable
fun DrawerItem(
    icon: ImageVector,
    text: String,
    isSelected: Boolean = false,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) Color(0xFF3B5998) else Color.Transparent
    val contentColor = Color.White

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .background(backgroundColor, shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 24.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            tint = contentColor
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = text,
            color = contentColor,
            fontSize = 16.sp
        )
    }
}

// --- Contenido principal de la pantalla de inicio ---
@Composable
fun HomeContent(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize()) {
        Text(
            text = "Inicio",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(16.dp)
        )
        // Aquí puedes agregar más contenido para la pantalla de inicio
    }
}


// --- Preview para visualizar el diseño ---
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    Sistema_ReservacionTheme {
        // Mostrar el contenido de la pantalla en preview sin requerir NavController
        HomeContent()
    }
}
