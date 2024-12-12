package com.example.pos

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pos.api.RetrofitInstance
import com.example.pos.api.dto.auth.LoginRequest
import com.example.pos.ui.theme.PosColor
import com.example.pos.ui.theme.PosRed
import com.example.pos.ui.theme.PosTheme
import com.example.pos.ui.theme.helveticaFamily
import com.example.pos.ui.theme.lobsterFamily
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PosTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LoginScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun LoginScreen(modifier: Modifier = Modifier) {
    var name by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val context = LocalContext.current

    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Login", fontFamily = lobsterFamily)

        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("name") })
        OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("password") }, visualTransformation = PasswordVisualTransformation())

        Button(onClick = { login(name, password, context) }, colors = ButtonDefaults.buttonColors(containerColor = PosRed)) {
            Text("Continue")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    PosTheme {
        LoginScreen()
    }
}

private fun login(name: String, password: String, context: Context) {
    val retrofitInstance = RetrofitInstance.api
    val sharedPref = context.getSharedPreferences("auth", Context.MODE_PRIVATE)

    CoroutineScope(Dispatchers.IO).launch {
        try {
            val response = retrofitInstance.login(LoginRequest(name, password))

            Log.d("LoginResponse", "Response: ${response.body()}")
            Log.d("LoginResponse", "Headers: ${response.headers()}")

            if (response.isSuccessful) {
                val sessionCookie = response.headers()["Set-Cookie"]?.split(";")?.get(0)
                if (sessionCookie != null) {
                    sharedPref.edit().putString("SESSION", sessionCookie).apply()

                    withContext(Dispatchers.Main) {
                        Log.d("Login", "Login successful")
                        context.startActivity(Intent(context, MainActivity::class.java))
                        (context as Activity).finish()
                    }
                }
            } else {
                withContext(Dispatchers.Main) {
                    Log.e("LoginError", "Invalid credentials: ${response.code()} - ${response.message()}")
                }
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Log.e("LoginError", "Login failed: ${e.message}")
            }
        }
    }
}