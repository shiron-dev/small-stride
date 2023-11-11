package dev.shiron.smallstride.view.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.shiron.smallstride.model.TargetClass
import dev.shiron.smallstride.ui.theme.SmallStrideTheme
import dev.shiron.smallstride.view.component.MyScaffold
import dev.shiron.smallstride.view.dummyTarget
import java.text.SimpleDateFormat
import java.util.Calendar

@SuppressLint("SimpleDateFormat")
@Composable
fun MilestoneCalenderScreen(navController: NavController, target: TargetClass, milestoneIndex: Int) {
    val milestone = target.milestones[milestoneIndex]
    MyScaffold(navController = navController, title = "ToDoの詳細を確認") {
        LazyColumn(contentPadding = it) {
            item {
                Column(
                    verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.Top),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 20.dp, top = 20.dp, end = 20.dp, bottom = 20.dp)
                ) {
                    Text(
                        "${target.endDayAt}日で${target.title}",
                        style = TextStyle(
                            fontSize = 24.sp,
                            fontWeight = FontWeight(400),
                            color = Color(0xFF000000)
                        )
                    )
                    Column(verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.Top)) {
                        Column {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(
                                    20.dp,
                                    Alignment.CenterHorizontally
                                )
                            ) {
                                Text(
                                    "Day${milestone.dayAt}",
                                    style = TextStyle(
                                        fontSize = 22.sp,
                                        fontWeight = FontWeight(400),
                                        color = Color(0xFF000000)
                                    )
                                )
                                val cal = Calendar.getInstance()
                                cal.time = target.startDay
                                cal.add(Calendar.DATE, milestone.dayAt)
                                val format = SimpleDateFormat("MM/dd")
                                Text(
                                    format.format(cal.time),
                                    style = TextStyle(
                                        fontSize = 22.sp,
                                        fontWeight = FontWeight(400),
                                        color = Color(0xFF000000)
                                    )
                                )
                            }
                            Text(
                                milestone.title,
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight(400),
                                    color = Color(0xFF000000)
                                )
                            )
                        }
                        Column {
                            Text(
                                "ヒント",
                                style = TextStyle(
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight(400),
                                    color = Color(0xFF000000)
                                )
                            )
                            Text(
                                text = milestone.hint,
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight(400),
                                    color = Color(0xFF000000)
                                )
                            )
                        }
                    }
                    Column {
                        Button(
                            onClick = {
                                navController.navigate("calender/target/${target.fileName}")
                            },
                            colors = ButtonDefaults.run { buttonColors(FloatingActionButtonDefaults.containerColor) },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                "目標の予定へ",
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
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MilestoneCalenderScreen() {
    SmallStrideTheme {
        MilestoneCalenderScreen(rememberNavController(), dummyTarget, 0)
    }
}
