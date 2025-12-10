package com.ivan.sistema_reservacion.ui.booking

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ivan.sistema_reservacion.ui.theme.Sistema_ReservacionTheme
import java.util.Calendar

@Composable
fun BookingScreen(onBack: () -> Unit) {
    // Estado para controlar qué vista mostrar: la inicial o el formulario.
    var showBookingForm by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f, fill = false), // Permite que el Card se encoja y no exceda la pantalla
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            // El contenido de la tarjeta cambia según el estado
            if (showBookingForm) {
                BookingForm(
                    onAcceptClick = {
                        // Aquí puedes añadir la lógica para guardar la reserva
                        // y después volver a la vista inicial.
                        showBookingForm = false
                    }
                )
            } else {
                InitialBookingView(
                    onCreateBookingClick = {
                        showBookingForm = true
                    },
                    onBackClick = onBack
                )
            }
        }
    }
}

/**
 * Vista inicial que se muestra cuando no hay reservas o se carga la pantalla por primera vez.
 */
@Composable
fun InitialBookingView(onCreateBookingClick: () -> Unit, onBackClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Reservaciones", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0D47A1))
        HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

        Text("Sin Reservaciones", fontSize = 16.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onCreateBookingClick,
            shape = RoundedCornerShape(50), // Botón redondeado
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF81C784)) // Tono verde
        ) {
            Text("Crear Reserva", color = Color.White)
        }
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedButton(
            onClick = onBackClick,
            shape = RoundedCornerShape(50)
        ) {
            Text("Volver")
        }
    }
}

/**
 * Formulario para crear una nueva reserva.
 */
@Composable
fun BookingForm(onAcceptClick: () -> Unit) {
    var clientName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var numPeople by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp)
            .verticalScroll(rememberScrollState()), // Habilita el scroll
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Reservación", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0D47A1))
        HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

        BookingTextField(value = clientName, onValueChange = { clientName = it }, label = "Nombre del cliente")
        BookingTextField(value = email, onValueChange = { email = it }, label = "Correo electronico", keyboardType = KeyboardType.Email)
        BookingDateField(value = date, onValueChange = { date = it }, label = "Fecha de reservacion")
        BookingTextField(value = numPeople, onValueChange = { numPeople = it }, label = "Num. de personas", keyboardType = KeyboardType.Number)
        BookingSpinner(label = "Area de reservación", items = listOf("Terraza", "Salón Principal", "Zona VIP"))
        BookingSpinner(label = "Mesas disponibles", items = listOf("Mesa 1", "Mesa 2", "Mesa 3"))
        BookingSpinner(label = "Servicios extras", items = listOf("Ninguno", "Boda", "Cumpleaños", "Reunión"))

        Spacer(modifier = Modifier.height(12.dp))

        errorMessage?.let {
            Text(
                text = it,
                color = Color.Red,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        Button(
            onClick = {
                if (clientName.isBlank() || email.isBlank() || date.isBlank() || numPeople.isBlank()) {
                    errorMessage = "Debe completar todos los campos vacios"
                } else {
                    errorMessage = null
                    onAcceptClick()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A237E)) // Azul oscuro
        ) {
            Text("ACEPTAR", modifier = Modifier.padding(vertical = 8.dp))
        }
    }
}

// --- Componentes Reutilizables para el Formulario ---

@Composable
fun BookingTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    Column(modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)) {
        Text(label, fontSize = 14.sp, color = Color.DarkGray, modifier = Modifier.padding(bottom = 4.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(label, color = Color.LightGray) },
            modifier = Modifier.fillMaxWidth().background(Color(0xFFF5F5F5), RoundedCornerShape(8.dp)),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent
            ),
            shape = RoundedCornerShape(8.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingDateField(value: String, onValueChange: (String) -> Unit, label: String) {
    var showDatePicker by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)) {
        Text(label, fontSize = 14.sp, color = Color.DarkGray, modifier = Modifier.padding(bottom = 4.dp))
        OutlinedTextField(
            value = value,
            onValueChange = {},
            readOnly = true,
            placeholder = { Text("DD / MM / AAAA", color = Color.LightGray) },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF5F5F5), RoundedCornerShape(8.dp))
                .clickable { showDatePicker = true },
            trailingIcon = {
                Icon(
                    Icons.Default.DateRange,
                    contentDescription = "Seleccionar fecha",
                    modifier = Modifier.clickable { showDatePicker = true }
                )
            },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent
            ),
            shape = RoundedCornerShape(8.dp)
        )
    }

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState()
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    showDatePicker = false
                    datePickerState.selectedDateMillis?.let { millis ->
                        val calendar = Calendar.getInstance()
                        calendar.timeInMillis = millis
                        val day = calendar.get(Calendar.DAY_OF_MONTH)
                        val month = calendar.get(Calendar.MONTH) + 1
                        val year = calendar.get(Calendar.YEAR)
                        val formattedDate = "${String.format("%02d", day)}/${String.format("%02d", month)}/$year"
                        onValueChange(formattedDate)
                    }
                }) {
                    Text("Aceptar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancelar")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

@Composable
fun BookingSpinner(label: String, items: List<String>) {
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf(items.firstOrNull() ?: "") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
    ) {
        Text(
            label,
            fontSize = 14.sp,
            color = Color.DarkGray,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        Box {
            OutlinedTextField(
                value = selectedItem,
                onValueChange = {}, // no editable
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = true }, // 👈 esto abre el menú
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Dropdown",
                        Modifier.clickable { expanded = true } // 👈 también aquí
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                ),
                shape = RoundedCornerShape(8.dp)
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                items.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(item) },
                        onClick = {
                            selectedItem = item
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

// --- Preview para visualizar el diseño ---
@Preview(showBackground = true, name = "Initial View")
@Composable
fun BookingScreenPreview() {
    Sistema_ReservacionTheme {
        // Envolvemos en un Box para simular el padding de la pantalla real
        Box(modifier = Modifier.background(Color.LightGray).padding(16.dp)) {
            BookingScreen(onBack = {})
        }
    }
}

@Preview(showBackground = true, name = "Booking Form")
@Composable
fun BookingFormPreview() {
    Sistema_ReservacionTheme {
        // Mostramos directamente el formulario para el preview
        Box(modifier = Modifier.background(Color.LightGray).padding(16.dp)) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                BookingForm(onAcceptClick = {})
            }
        }
    }
}
