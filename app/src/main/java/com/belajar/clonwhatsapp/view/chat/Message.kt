package com.belajar.clonwhatsapp.view.chat

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.belajar.clonwhatsapp.R
import com.belajar.clonwhatsapp.model.SampleData
import com.belajar.clonwhatsapp.ui.theme.TealGreen
import com.belajar.clonwhatsapp.ui.theme.TealGreenDark
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun Message(navController: NavController) {
    val viewModel: WhatsAppChatViewModel = viewModel()
    val getAllChat = viewModel.getDataSample.observeAsState(mutableListOf())
    val flag = viewModel.flag.observeAsState()
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        topBar = { TopBarMessage(navController) },
        bottomBar = { BottomBarMessage(viewModel) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFECE5DD))
        ) {
            Text(text = "Today ${flag.value}")
            Spacer(modifier = Modifier.height(5.dp))
            Surface {
                Scaffold(modifier = Modifier.fillMaxSize(), scaffoldState = scaffoldState) {
                    LazyColumn(modifier = Modifier.padding(horizontal = 10.dp)) {
                        items(getAllChat.value.size) { index ->
                            MessageListItem(getAllChat.value[index], index)
                        }
                    }
                }
            }
        }
    }
}

@SuppressLint("SimpleDateFormat")
@Composable
fun BottomBarMessage(viewModel: WhatsAppChatViewModel) {
    val textState = remember { mutableStateOf(TextFieldValue()) }
    val date = SimpleDateFormat("hh.mm a")
    val strDate: String = date.format(Date())

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Row(
            modifier = Modifier
                .weight(0.85f)
                .wrapContentSize()
                .background(color = Color.White, shape = RoundedCornerShape(30.dp))
                .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_insert_emoticon_24),
                contentDescription = "Emoji",
                tint = Color.Gray,
                modifier = Modifier
                    .weight(0.1f)
                    .padding(bottom = 17.dp)
            )
            TextField(
                value = textState.value,
                onValueChange = { textState.value = it },
                placeholder = { Text(text = "Message", color = Color.Gray, fontSize = 15.sp) },
                modifier = Modifier
                    .weight(0.66f)
                    .wrapContentHeight(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
                colors = TextFieldDefaults.textFieldColors(
                    disabledTextColor = Color.Transparent,
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                maxLines = 6
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_attach_file_24),
                contentDescription = "Add file",
                tint = Color.Gray,
                modifier = Modifier
                    .weight(0.14f)
                    .rotate(-45f)
                    .padding(bottom = 17.dp)
            )
            if (textState.value.text.isBlank())
                Icon(
                    painter = painterResource(id = R.drawable.ic_photo_camera_24),
                    contentDescription = "Add file",
                    tint = Color.Gray,
                    modifier = Modifier
                        .weight(0.1f)
                        .padding(bottom = 17.dp)
                )
        }
        FloatingActionButton(
            onClick = {
                if (textState.value.text.isNotBlank()) {
                    val data = SampleData(
                        name = "Name",
                        desc = textState.value.text,
                        imgUrl = "SampleUrl",
                        createDate = strDate
                    )
                    viewModel.addChat(data)
                }
            },
            backgroundColor = TealGreen,
            elevation = FloatingActionButtonDefaults.elevation(0.dp, 0.dp)
        ) {
            Icon(
                painter = painterResource(
                    id = if (textState.value.text.isBlank()) R.drawable.ic_mic_24 else R.drawable.ic_send_24
                ),
                contentDescription = "FAB",
                tint = Color.White,
                modifier = Modifier.padding(10.dp)
            )
        }
    }
}

@Composable
fun MessageListItem(data: SampleData, index: Int) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp)
    ) {
        if (index.mod(2) == 0) {
            Row {
                Spacer(modifier = Modifier.width(50.dp))
                Column(
                    modifier = Modifier
                        .background(color = Color(0xFFDCF8C6), shape = RoundedCornerShape(10.dp))
                        .padding(5.dp),
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = data.desc,
                        color = Color.Black,
                        fontSize = 15.sp,
                        textAlign = TextAlign.Justify
                    )
                    Spacer(modifier = Modifier.width(7.dp))
                    Text(
                        text = data.createDate,
                        color = Color.Gray,
                        fontSize = 10.sp
                    )
                }
            }
        } else {
            Row {
                Column(
                    modifier = Modifier
                        .background(color = Color.White, shape = RoundedCornerShape(10.dp))
                        .padding(5.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = data.desc,
                        color = Color.Black,
                        fontSize = 15.sp,
                        textAlign = TextAlign.Justify
                    )
                    Spacer(modifier = Modifier.width(7.dp))
                    Text(
                        text = data.createDate,
                        color = Color.Gray,
                        fontSize = 10.sp
                    )
                }
                Spacer(modifier = Modifier.width(50.dp))
            }
        }
    }
}

@Composable
fun TopBarMessage(navController: NavController) {
    val menuExpanded = remember { mutableStateOf(false) }
    val menuOtherExpand = remember { mutableStateOf(false) }
    val context = LocalContext.current

    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_24),
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier
                        .padding(end = 5.dp)
                        .clickable { navController.navigateUp() }
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_person_24),
                    contentDescription = "Image Contact",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(Color.Gray)
                )
                Column(
                    modifier = Modifier
                        .padding(vertical = 5.dp)
                        .padding(start = 5.dp)
                ) {
                    Text(text = "Name 1", color = Color.White, fontSize = 15.sp)
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(text = "Online", color = Color(0XFFBBCECD), fontSize = 13.sp)
                }
            }
        },
        actions = {
            IconButton(onClick = {
                Toast.makeText(context, "Clicked Video Call", Toast.LENGTH_SHORT).show()
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_videocam_24),
                    contentDescription = "Search",
                    tint = Color.White
                )
            }
            IconButton(onClick = {
                Toast.makeText(context, "Clicked Call", Toast.LENGTH_SHORT).show()
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_phone_24),
                    contentDescription = "Search",
                    tint = Color.White
                )
            }
            IconButton(onClick = {
                menuExpanded.value = true
            }) {
                Icon(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = "Menu",
                    tint = Color.White
                )
            }
            DropdownMenu(
                modifier = Modifier.width(200.dp),
                expanded = menuExpanded.value,
                onDismissRequest = { menuExpanded.value = false }
            ) {
                DropdownMenuItem(onClick = { /*TODO*/ }) {
                    Text(text = "See contact")
                }
                DropdownMenuItem(onClick = { /*TODO*/ }) {
                    Text(text = "Media, link, and document")
                }
                DropdownMenuItem(onClick = { /*TODO*/ }) {
                    Text(text = "Search")
                }
                DropdownMenuItem(onClick = { /*TODO*/ }) {
                    Text(text = "Silent Notification")
                }
                DropdownMenuItem(onClick = { /*TODO*/ }) {
                    Text(text = "Wallpaper")
                }
                DropdownMenuItem(onClick = {
                    menuExpanded.value = false
                    menuOtherExpand.value = true
                }) {
                    Text(text = "Other")
                }
            }

            // Dropdown Other
            DropdownMenu(
                modifier = Modifier.width(150.dp),
                expanded = menuOtherExpand.value,
                onDismissRequest = { menuOtherExpand.value = false }
            ) {
                DropdownMenuItem(onClick = { /*TODO*/ }) {
                    Text(text = "Report")
                }
                DropdownMenuItem(onClick = { /*TODO*/ }) {
                    Text(text = "Block")
                }
                DropdownMenuItem(onClick = { /*TODO*/ }) {
                    Text(text = "Clean Chat")
                }
                DropdownMenuItem(onClick = { /*TODO*/ }) {
                    Text(text = "Export Chat")
                }
            }
        },
        backgroundColor = TealGreenDark,
        elevation = 0.dp
    )
}

@Preview
@Composable
fun PreviewMessage() {
    Message(navController = rememberNavController())
}