package com.tu.paquete.botonmensaje

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

val TurquesaApp = Color(0, 206, 209)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(colorScheme = lightColorScheme(primary = TurquesaApp)) {
                Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
                    WhatsAppSenderScreen()
                }
            }
        }
    }
}

@Composable
fun WhatsAppSenderScreen() {
    val context = LocalContext.current
    var phoneNumber by remember { mutableStateOf("") }
    var messageText by remember { mutableStateOf("") }

    val maxPhoneDigits = 8
    val maxMessageChars = 200
    val codigoPais = "507"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Enviar mensaje a Whatsapp",
            style = MaterialTheme.typography.headlineMedium,
            color = TurquesaApp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(40.dp))

        // SECCIÓN: NÚMERO DE TELÉFONO
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Número de teléfono",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.DarkGray,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            OutlinedTextField(
                value = phoneNumber,
                onValueChange = { if (it.all { c -> c.isDigit() } && it.length <= maxPhoneDigits) phoneNumber = it },
                prefix = { Text("+$codigoPais ") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                placeholder = { Text("Ej: 66666666") },
                supportingText = { Text("${phoneNumber.length} / $maxPhoneDigits dígitos") }
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // SECCIÓN: MENSAJE
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Mensaje",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.DarkGray,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            OutlinedTextField(
                value = messageText,
                onValueChange = { if (it.length <= maxMessageChars) messageText = it },
                modifier = Modifier.fillMaxWidth(),
                minLines = 4,
                placeholder = { Text("Escribe tu mensaje aquí...") },
                supportingText = {
                    Text(
                        text = "${messageText.length} / $maxMessageChars",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.End
                    )
                }
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                if (phoneNumber.length == maxPhoneDigits && messageText.isNotBlank()) {
                    val numCompleto = codigoPais + phoneNumber
                    enviarWA(context, numCompleto, messageText)
                } else {
                    Toast.makeText(context, "Verifique los datos (8 dígitos y mensaje)", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = TurquesaApp)
        ) {
            Text("Enviar a WhatsApp", color = Color.White, fontWeight = FontWeight.Bold)
        }
    }
}

private fun enviarWA(context: Context, fone: String, msg: String) {
    try {
        val encodedMsg = URLEncoder.encode(msg, StandardCharsets.UTF_8.toString())
        val url = "https://api.whatsapp.com/send?phone=$fone&text=$encodedMsg"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
    } catch (e: Exception) {
        Toast.makeText(context, "WhatsApp no instalado", Toast.LENGTH_LONG).show()
    }
}