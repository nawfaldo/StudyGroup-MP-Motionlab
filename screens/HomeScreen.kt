package com.example.pos.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pos.model.BookModel
import com.example.pos.ui.theme.helveticaFamily



@Composable
fun HomeContent() {
    val books = listOf(
        BookModel("title", "desc", ""),
        BookModel("title", "desc", ""),
        BookModel("title", "desc", "")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn {
//            items(10) {
//                BookItem()
//            }
            items(books) {book ->
                BookItem(
                    book.title,
                    onClick = {
//                        navController.navigate()
                    }
                )
            }
        }
    }
}

@Composable
fun BookItem(title: String, onClick: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(16.dp)) {
        Row(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Box(modifier = Modifier.size(16.dp).background(color = Color.Gray))

            Column {
                Text(title, fontFamily = helveticaFamily, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Text("Description", fontFamily = helveticaFamily, fontWeight = FontWeight.Normal, fontSize = 14.sp)
            }
        }
    }
}