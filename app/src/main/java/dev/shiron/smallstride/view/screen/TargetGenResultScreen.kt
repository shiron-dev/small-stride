package dev.shiron.smallstride.view.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.shiron.smallstride.model.MilestoneClass
import dev.shiron.smallstride.model.TargetClass
import dev.shiron.smallstride.repository.saveTarget
import dev.shiron.smallstride.ui.theme.SmallStrideTheme
import dev.shiron.smallstride.view.ScreenEnum
import dev.shiron.smallstride.view.dummyTarget
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TargetGenResultScreen(navController: NavController, target: TargetClass) {
    val context = LocalContext.current

    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(topAppBarState)

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "${target.endDayAt}日で${target.title}\n",
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                },
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier
                    .height(150.dp)
                    .clip(RoundedCornerShape(30.dp, 30.dp, 0.dp, 0.dp))
            ) {
                Column {
                    Button(
                        onClick = {
                            navController.run { navigate("target/create/${target.input}/${target.endDayAt}") }
                        },
                        colors = ButtonDefaults.run { buttonColors(FloatingActionButtonDefaults.containerColor) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            "変更",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight(400),
                                color = Color.DarkGray
                            )
                        )
                    }
                    Button(
                        onClick = {
                            saveTarget(context, target)
                            navController.navigate(ScreenEnum.HOME.route)
                        },
                        colors = ButtonDefaults.run { buttonColors(FloatingActionButtonDefaults.containerColor) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            "登録",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight(400),
                                color = Color.DarkGray
                            )
                        )
                    }
                    val uriHandler = LocalUriHandler.current
                    Button(
                        onClick = {
                            saveTarget(context, target)
                            uriHandler.openUri("https://pck.shiron.dev/calender?target=${target.fileName}")
                            navController.navigate(ScreenEnum.HOME.route)
                        },
                        colors = ButtonDefaults.run { buttonColors(FloatingActionButtonDefaults.containerColor) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            "Googleカレンダーに登録",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight(400),
                                color = Color.DarkGray
                            )
                        )
                    }
                }
            }
        }
    ) {
        LazyColumn(contentPadding = it, verticalArrangement = Arrangement.spacedBy(5.dp), modifier = Modifier.padding(20.dp)) {
            items(target.milestones.size) { it1 ->
                MilestoneContent(target.startDay, target.milestones[it1])
            }
        }
    }
}

@SuppressLint("SimpleDateFormat")
@Composable
fun MilestoneContent(startDay: Date, milestoneClass: MilestoneClass) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.Start),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = Color(0xFF000000),
                shape = RoundedCornerShape(size = 4.dp)
            )
            .padding(start = 4.dp, top = 4.dp, end = 4.dp, bottom = 4.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("${milestoneClass.dayAt}日目")
            val calendar = Calendar.getInstance()
            calendar.time = startDay
            calendar.add(Calendar.DATE, milestoneClass.dayAt)
            val sdf = SimpleDateFormat("MM/dd")
            Text(sdf.format(calendar.time))
        }
        Text(
            text = milestoneClass.title,
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight(400),
                color = Color(0xFF000000)
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TargetGenResultScreenPreview() {
    SmallStrideTheme {
        TargetGenResultScreen(rememberNavController(), dummyTarget)
    }
}
