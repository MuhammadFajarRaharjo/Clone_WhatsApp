package com.belajar.clonwhatsapp.view

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import com.belajar.clonwhatsapp.R
import com.belajar.clonwhatsapp.model.SampleData
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
@Composable
fun WhatsAppStatus() {
    val date = SimpleDateFormat("EEEE hh.mm")
    val strDate: String = date.format(Date())
    val data = mutableListOf<SampleData>()
    repeat(5) {
        data.add(
            SampleData(
                name = "Status ${it + 1}",
                imgUrl = "sample Url",
                createDate = strDate
            )
        )
    }

    LazyColumn(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
    ) {
        item {
            Column(Modifier.fillMaxSize()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_person_24),
                        contentDescription = "My Status",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                    )
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .padding(top = 3.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "My Status",
                            fontSize = 15.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Tap to add status update",
                            fontSize = 14.sp,
                            color = Color.Gray,
                            fontWeight = FontWeight.Normal
                            // maxLines = 1,
                            // overflow = TextOverflow.Ellipsis,
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.LightGray)
                ) {
                    Text(
                        text = "Recent update",
                        fontSize = 15.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(15.dp, 5.dp, 10.dp, 5.dp)
                    )
                }
            }
        }
        items(data.size) { index ->
            StatusListItem(data[index], data[data.lastIndex].name)
        }
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray)
            ) {
                Text(
                    text = "Seen updates",
                    fontSize = 15.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(15.dp, 5.dp, 10.dp, 5.dp)
                )
            }
        }
        items(data.size) { index ->
            StatusListItem(data[index], data[data.lastIndex].name)
        }
    }
}

@Composable
fun StatusListItem(sampleData: SampleData, nameLastIndex: String = "") {
    Column {
        Box(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .padding(5.dp)
            ) {
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
                        .fillMaxWidth()
                ) {
                    Text(
                        text = sampleData.name,
                        fontSize = 15.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = sampleData.createDate,
                        fontSize = 14.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Normal,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    if (sampleData.name != nameLastIndex)
                        Divider(modifier = Modifier.fillMaxWidth(), color = Color.LightGray)
                }
            }
        }
    }
}

// @ExperimentalPagerApi
@Composable
@Preview
fun PreviewStatus() {
    WhatsAppStatus()
}