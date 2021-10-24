package com.belajar.clonwhatsapp.view.chat

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.belajar.clonwhatsapp.R
import com.belajar.clonwhatsapp.model.SampleData
import com.belajar.clonwhatsapp.utils.Screen
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
@Composable
fun WhatsAppChats(navController: NavController) {
    val date = SimpleDateFormat("hh.mm a")
    val strDate: String = date.format(Date())
    val data = mutableListOf<SampleData>()
    repeat(20) {
        data.add(
            SampleData(
                name = "Name ${it + 1}",
                desc = "Clone",
                imgUrl = "sample Url",
                createDate = strDate
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .wrapContentSize(Alignment.Center)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp)
                .padding(top = 10.dp)
        ) {
            items(data.size) { index ->
                ChatListItem(data[index], navController)
            }
        }
    }
}

@Composable
fun ChatListItem(sampleData: SampleData, navController: NavController) {
    Column {
        Box(modifier = Modifier.fillMaxWidth()) {
            Row(modifier = Modifier.padding(vertical = 5.dp).clickable { navController.navigate(Screen.Message.route) }) {
                Image(
                    painter = painterResource(id = R.drawable.ic_person_24),
                    contentDescription = "Contact Image",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(Color.Gray)
                )

                Column(
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .padding(top = 3.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = sampleData.name,
                            modifier = Modifier.weight(0.7f),
                            fontSize = 15.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = sampleData.createDate,
                            modifier = Modifier.weight(0.2f),
                            fontSize = 12.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.End
                        )
                    }
                    Text(
                        text = sampleData.desc,
                        fontSize = 14.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Normal,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.fillMaxWidth(0.7f)
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    Divider(modifier = Modifier.fillMaxWidth(), color = Color.LightGray)
                }
            }
        }
    }
}

@Composable
@Preview
fun PreviewChat() {
    WhatsAppChats(navController = rememberNavController())
}