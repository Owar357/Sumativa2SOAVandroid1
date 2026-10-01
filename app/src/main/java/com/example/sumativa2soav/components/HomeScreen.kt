package com.example.sumativa2soav.components
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp



@Composable
fun HomeScreen(
){
    var nombrePlatillo by remember { mutableStateOf("") }
    var cantidad by remember { mutableStateOf("") }
    var precioUnitario by remember { mutableStateOf("") }
    var porcentajePropina by remember { mutableStateOf("") }
    var propina  by remember { mutableStateOf(0.0) }
    var totalPagar by remember { mutableStateOf(0.0) }
    var total by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    Column(
         modifier = Modifier
             .fillMaxSize()
             .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ResultCard ("Propina", propina, Modifier.weight(1f))
            ResultCard("Total a Pagar", totalPagar, Modifier.weight(1f))
        }

        OutlinedTextField(
            value = nombrePlatillo,
            onValueChange = {nombrePlatillo = it },
            label = { Text("Nombre del platillo") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            singleLine = true,
            isError = error != null,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = cantidad,
            onValueChange = { cantidad = it },
            label = { Text("Cantidad") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            singleLine = true,
            isError = error != null,
            modifier = Modifier.fillMaxWidth()
        )


        OutlinedTextField(
            value = precioUnitario,
            onValueChange = { precioUnitario = it },
            label = { Text("Precio Unitario") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            singleLine = true,
            isError = error != null,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = porcentajePropina,
            onValueChange = { porcentajePropina = it },
            label = { Text("Porcentaje de propina (máx 10%)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            singleLine = true,
            isError = error != null,
            modifier = Modifier.fillMaxWidth()
        )

        // Mostramos mensaje en caso de error
        error?.let {
            Text(text = it, color = MaterialTheme.colorScheme.error)
        }
        Button(
            onClick = {
                val p = porcentajePropina.toDoubleOrNull()
                val c = cantidad.toDoubleOrNull()
                val pu = precioUnitario.toDoubleOrNull()

                when {
                    p == null || c == null || pu == null -> error = "Digite valores numéricos válidos"
                    p !in 0.0..10.0 -> error = "El descuento debe ser entre 0.0 y 10"
                    else -> {
                        val subTotal = c * pu
                        propina = subTotal * (p/100.0)
                        totalPagar =  subTotal + propina
                        error = null
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calcular Total")
        }

        OutlinedButton(
            onClick = {
                nombrePlatillo = ""
                cantidad = ""
                precioUnitario  = ""
                porcentajePropina = ""
                propina = 0.00
                totalPagar = 0.00
                error = null
            },
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colorScheme.error
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Limpiar")
        }
    }
  }



