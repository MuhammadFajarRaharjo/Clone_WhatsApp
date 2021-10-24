package com.belajar.clonwhatsapp

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.belajar.clonwhatsapp.ui.theme.ClonWhatsAppTheme
import com.belajar.clonwhatsapp.ui.theme.TealGreenDark
import com.belajar.clonwhatsapp.utils.Constants._tabCurrentStatus
import com.belajar.clonwhatsapp.utils.Constants.tabCurrentStatus
import com.belajar.clonwhatsapp.utils.Screen
import com.belajar.clonwhatsapp.view.chat.WhatsAppChats
import com.belajar.clonwhatsapp.view.WhatsAppStatus
import com.belajar.clonwhatsapp.view.WhatsAppCall
import com.belajar.clonwhatsapp.view.chat.Message
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @ExperimentalPagerApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ClonWhatsAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Chat.route
                    ) {
                        composable(Screen.Chat.route) { WhatsApp(navController) }
                        composable(Screen.Message.route) { Message(navController) }
                    }
                }
            }
        }
    }
}

@ExperimentalPagerApi
@Composable
fun WhatsApp(navController: NavController) {
    val context = LocalContext.current
    val tabStatus = tabCurrentStatus.observeAsState()

    Scaffold(
        topBar = { TopBarWhatsApp(tabStatus, context) },
        content = { WhatsAppTab(navController) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { Toast.makeText(context, "FAB Clicked", Toast.LENGTH_SHORT).show() },
                backgroundColor = Color.Green,
                elevation = FloatingActionButtonDefaults.elevation(),
                modifier = Modifier.padding(10.dp),
                shape = CircleShape
            ) {
                when (tabStatus.value) {
                    0 -> Icon(
                        painter = painterResource(id = R.drawable.ic_message_24),
                        contentDescription = "Message",
                        tint = Color.White,
                        modifier = Modifier.padding(10.dp)
                    )
                    1 -> Icon(
                        painter = painterResource(id = R.drawable.ic_photo_camera_24),
                        contentDescription = "Camera",
                        tint = Color.White,
                        modifier = Modifier.padding(10.dp)
                    )
                    2 -> Icon(
                        painter = painterResource(id = R.drawable.ic_add_call_24),
                        contentDescription = "Add Call",
                        tint = Color.White,
                        modifier = Modifier.padding(10.dp)
                    )
                }
            }
        }
    )
}

@Composable
fun TopBarWhatsApp(tabStatus: State<Int?>, context: Context) {
    val menuExpanded = remember { mutableStateOf(false) }

    TopAppBar(
        title = {
            Text(
                text = "Cloning WhatsApp",
                color = Color.White,
                fontSize = 20.sp
            )
        },
        actions = {
            IconButton(onClick = {
                Toast.makeText(context, "Clicked Search", Toast.LENGTH_SHORT).show()
            }) {
                Icon(
                    imageVector = Icons.Filled.Search,
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
                modifier = Modifier
                    .width(200.dp),
                expanded = menuExpanded.value,
                onDismissRequest = { menuExpanded.value = false }
            ) {
                when (tabStatus.value) {
                    0 -> {
                        DropdownMenuItem(onClick = { /*HANDLE NEW GROUP*/ }) {
                            Text(text = "New group")
                        }
                        DropdownMenuItem(onClick = { /*HANDLE NEW BROADCAST*/ }) {
                            Text(text = "New broadCast")
                        }
                        DropdownMenuItem(onClick = { /*HANDLE LINKED DEVICE*/ }) {
                            Text(text = "Linked devices")
                        }
                        DropdownMenuItem(onClick = { /*HANDLE STAR MESSAGES*/ }) {
                            Text(text = "Star messages")
                        }
                        DropdownMenuItem(onClick = { /*HANDLE SETTINGS*/ }) {
                            Text(text = "Settings")
                        }
                    }
                    1 -> {
                        DropdownMenuItem(onClick = { /*HANDLE STATUS PRIVACY*/ }) {
                            Text(text = "Status privacy")
                        }
                        DropdownMenuItem(onClick = { /*HANDLE SETTINGS*/ }) {
                            Text(text = "Settings")
                        }
                    }
                    2 -> {

                        DropdownMenuItem(onClick = { /*HANDLE CLEAR CALL LOG*/ }) {
                            Text(text = "Clear call log")
                        }
                        DropdownMenuItem(onClick = { /*HANDLE SETTINGS*/ }) {
                            Text(text = "Settings")
                        }
                    }
                }
            }
        },
        backgroundColor = TealGreenDark,
        elevation = 0.dp
    )
}

@ExperimentalPagerApi
@Composable
fun WhatsAppTab(navController: NavController) {
    val pagerState = rememberPagerState()
    Column(modifier = Modifier.fillMaxSize()) {
        Tabs(pagerState)
        TabsContent(pagerState, navController)
    }
}

@ExperimentalPagerApi
@Composable
fun Tabs(pagerState: PagerState) {
    val list = listOf("CHAT", "STATUS", "TELEPHONE")
    val scope = rememberCoroutineScope()
    _tabCurrentStatus.value = pagerState.currentPage

    TabRow(
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = TealGreenDark,
        contentColor = Color.White
        ,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                modifier = Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                height = 2.dp,
                color = Color.White
            )
        }
    ) {
        list.forEachIndexed { index, s ->
            Tab(
                text = {
                    Text(
                        text = s,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                },
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                }
            )
        }
    }
}

@ExperimentalPagerApi
@Composable
fun TabsContent(pagerState: PagerState, navController: NavController) {
    HorizontalPager(state = pagerState, count = 3) { pageIndex ->
        when (pageIndex) {
            0 -> WhatsAppChats(navController)
            1 -> WhatsAppStatus()
            2 -> WhatsAppCall()
        }
    }
}

@ExperimentalPagerApi
@Composable
@Preview
fun PreviewMain() {
    WhatsApp(rememberNavController())
}