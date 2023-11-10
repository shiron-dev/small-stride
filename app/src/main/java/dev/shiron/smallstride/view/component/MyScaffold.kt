package dev.shiron.smallstride.view.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import dev.shiron.smallstride.view.ScreenEnum

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyScaffold(navController: NavController, title: String, content: @Composable (PaddingValues) -> Unit) {
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(topAppBarState)

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = title)
                },
                navigationIcon = {
                    // BackIconButton {}
                },
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier
                    .height(70.dp)
                    .clip(RoundedCornerShape(30.dp, 30.dp, 0.dp, 0.dp))
            ) {
                NavigationBar {
                    NavigationBarItem(
                        selected = currentDestination?.route == ScreenEnum.HOME.route,
                        onClick = {
                            navController.navigate(ScreenEnum.HOME.route)
                        },
                        icon = {
                            Icon(imageVector = Icons.Filled.Home, contentDescription = null)
                        }
                    )
                    NavigationBarItem(
                        selected = currentDestination?.route == ScreenEnum.ALL_CALENDER.route,
                        onClick = {
                            navController.navigate(ScreenEnum.ALL_CALENDER.route)
                        },
                        icon = {
                            Icon(imageVector = Icons.Filled.DateRange, contentDescription = null)
                        }
                    )
                }
                Button(
                    onClick = { navController.navigate("calender/all") },
                    colors = ButtonDefaults.run { buttonColors(Color(0xFF80A8FF)) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "予定画面へ",
                        style = TextStyle(
                            fontSize = 24.sp,
                            fontWeight = FontWeight(500),
                            color = Color(0xFFFFFFFF),
                            textAlign = TextAlign.Center
                        )
                    )
                }
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(ScreenEnum.TARGET_CREATE.route)
                }
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = null)
            }
        },
        content = content
    )
}
