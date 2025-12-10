package com.ivan.sistema_reservacion

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.ivan.sistema_reservacion.ui.theme.Sistema_ReservacionTheme

@Composable
fun RegisterDialog(onDismissRequest: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Dialog es un composable que muestra contenido en una ventana emergente.
    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Sign up",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(24.dp))

                // Campo de Nombre
                CustomTextField(
                    value = name,
                    onValueChange = { name = it },
                    placeholder = "Name"
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Campo de Apellido
                CustomTextField(
                    value = lastName,
                    onValueChange = { lastName = it },
                    placeholder = "Last name"
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Campo de Email
                CustomTextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = "Email",
                    keyboardType = KeyboardType.Email
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Campo de Teléfono
                CustomTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    placeholder = "Phone",
                    keyboardType = KeyboardType.Phone
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Campo de Contraseña
                CustomTextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = "Password",
                    isPassword = true
                )
                Spacer(modifier = Modifier.height(24.dp))

                // Botón de Confirmar
                Button(
                    onClick = { /* TODO: Lógica de registro */ },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4A90E2))
                ) {
                    Text(text = "Confirmar", modifier = Modifier.padding(vertical = 8.dp))
                }
            }
        }
    }
}

/**
 * Un campo de texto personalizado reutilizable para mantener el estilo consistente.
 */
@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    isPassword: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(placeholder) },
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF0F0F0), RoundedCornerShape(8.dp)), // Fondo gris claro
        singleLine = true,
        visualTransformation = if (isPassword) PasswordVisualTransformation() else androidx.compose.ui.text.input.VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        colors = OutlinedTextFieldDefaults.colors(
            // Oculta los bordes para que solo se vea el fondo
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent
        ),
        shape = RoundedCornerShape(8.dp)
    )
}

// Nueva pantalla de registro completa que se usará en la navegación
@Composable
fun RegisterScreen(navController: NavController) {
    var name by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Registro", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(16.dp))

            CustomTextField(value = name, onValueChange = { name = it }, placeholder = "Name")
            Spacer(modifier = Modifier.height(12.dp))
            CustomTextField(value = lastName, onValueChange = { lastName = it }, placeholder = "Last name")
            Spacer(modifier = Modifier.height(12.dp))
            CustomTextField(value = email, onValueChange = { email = it }, placeholder = "Email", keyboardType = KeyboardType.Email)
            Spacer(modifier = Modifier.height(12.dp))
            CustomTextField(value = phone, onValueChange = { phone = it }, placeholder = "Phone", keyboardType = KeyboardType.Phone)
            Spacer(modifier = Modifier.height(12.dp))
            CustomTextField(value = password, onValueChange = { password = it }, placeholder = "Password", isPassword = true)

            if (errorMessage != null) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = errorMessage!!, color = Color.Red)
            }

            Spacer(modifier = Modifier.height(24.dp))
            Button(onClick = {
                errorMessage = null
                if (name.isBlank() || email.isBlank() || password.isBlank()) {
                    errorMessage = "Completa los campos obligatorios"
                } else {
                    // TODO: lógica de registro real (API, guardado local, etc.)
                    // Tras registrar, navegar a Login
                    navController.navigate("login") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(8.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4A90E2))) {
                Text(text = "Crear cuenta", modifier = Modifier.padding(vertical = 8.dp))
            }

            Spacer(modifier = Modifier.height(12.dp))
            TextButton(onClick = { navController.popBackStack() }) {
                Text(text = "Volver al inicio")
            }
        }
    }
}

// Preview para visualizar el diálogo de registro de forma aislada
@Preview(showBackground = true, backgroundColor = 0x808080) // Fondo semitransparente para simular el efecto
@Composable
fun RegisterDialogPreview() {
    Sistema_ReservacionTheme {
        // En el preview, envolvemos el diálogo en un Box para poder verlo
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Gray.copy(alpha = 0.5f)), // Simula el fondo oscuro
            contentAlignment = Alignment.Center
        ) {
            RegisterDialog(onDismissRequest = {})
        }
    }
}
