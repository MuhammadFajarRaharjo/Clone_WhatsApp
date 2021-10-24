package com.belajar.clonwhatsapp.view

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.belajar.clonwhatsapp.R
import com.belajar.clonwhatsapp.model.SampleData
import com.belajar.clonwhatsapp.ui.theme.TealGreenDark
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
@Composable
fun WhatsAppCall() {
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
                WhatsAppCallItems(data[index], index)
            }
        }
    }
}

@Composable
fun WhatsAppCallItems(sampleData: SampleData, index: Int) {
    val context = LocalContext.current
    val random = (0..20).random()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_person_24),
            contentDescription = "Contact Image",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .background(Color.Gray)
        )
        Column(
            modifier = Modifier
                .padding(10.dp)
                .weight(0.7f)
        ) {
            Text(
                text = sampleData.name,
                fontSize = 15.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(5.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (index.mod(2) == 0) {
                    Icon(
                        painter = painterResource(
                            id = R.drawable.ic_arrow_24
                        ),
                        contentDescription = "Call Out",
                        modifier = Modifier.rotate(135f),
                        tint = Color.Green
                    )
                } else {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_24),
                        contentDescription = "Incoming Call",
                        modifier = Modifier.rotate(315f),
                        tint = Color.Red
                    )
                }

                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = sampleData.createDate,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.Normal
                )
            }
        }
        IconButton(onClick = { Toast.makeText(context, "call", Toast.LENGTH_SHORT).show() }) {
            Icon(
                painter = painterResource(id = if (index != random) R.drawable.ic_phone_24 else R.drawable.ic_videocam_24),
                contentDescription = "Call",
                tint = TealGreenDark
            )
        }
    }
}

@Composable
@Preview
fun PreviewTelephone() {
    WhatsAppCall()
}
