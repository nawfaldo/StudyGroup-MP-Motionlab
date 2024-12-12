package com.example.pos

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.example.pos.api.RetrofitInstance
import com.example.pos.screens.HomeContent
import com.example.pos.ui.theme.PosTheme
import com.example.pos.ui.theme.helveticaFamily
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            checkAuthentication()
        }
    }

    private suspend fun checkAuthentication() {
        val sharedPref = getSharedPreferences("auth", MODE_PRIVATE)
        val sessionCookie = sharedPref.getString("SESSION", null)

        if (sessionCookie.isNullOrEmpty()) {
            redirectToLogin()
            return
        }

        val response = RetrofitInstance.api.getCurrentUser(sessionCookie)
        if (response.isSuccessful) {
            setContent {
                PosTheme {
                    MainScreen(onLoginClick = { logoutUser() }, username = response.body()?.name ?: "")
                }
            }
        } else {
            redirectToLogin()
        }
    }

    private fun redirectToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun logoutUser() {
        val sharedPref = getSharedPreferences("auth", MODE_PRIVATE)
        sharedPref.edit().remove("SESSION").apply()
        redirectToLogin()
    }
}

data class NavigationItem(
    val title : String,
    val iconSelected : ImageVector,
    val iconUnselected : ImageVector
)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(modifier: Modifier = Modifier, onLoginClick: () -> Unit,  username: String) {
    val screens = listOf(
        NavigationItem("Home", Icons.Filled.Home, Icons.Outlined.Home),
        NavigationItem("Profile", Icons.Filled.Person, Icons.Outlined.Person),
        NavigationItem("Settings", Icons.Filled.Settings, Icons.Outlined.Settings)
    )
    var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }

        Scaffold(
            bottomBar = {
                NavigationBar(

                ) {
                    screens.forEachIndexed { index, s ->
                        NavigationBarItem(
                            icon = { if (selectedItemIndex == index) s.iconSelected else s.iconUnselected },
                            label = { Text(s.title) },
                            selected = selectedItemIndex == index,
                            onClick = {
                                selectedItemIndex = index
                            }
                        )
                    }
                }
            },
            content = {
                Column (
                    modifier = Modifier.fillMaxSize().padding(16.dp)
                ) {
                    when(selectedItemIndex) {
                        0 -> HomeContent()
                        1 -> ProfileContent(onLoginClick, username)
                        2 -> SettingsContent()
                    }
                }
            }
        )

}

@Composable
fun ProfileContent(onLoginClick: () -> Unit,  username: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "$username!")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onLoginClick) {
            Text("Logout")
        }
    }
}

@Composable
fun SettingsContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Settings")
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    PosTheme {
        MainScreen(onLoginClick = {}, username = "anon")
    }
}

//@Serializable