package dev.shiron.smallstride.view.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
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

@Composable
fun TargetCalenderScreen(navController: NavController, target: TargetClass) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
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
        MilestonesList(navController, target)
    }
}

@Composable
private fun MilestonesList(navController: NavController, target: TargetClass) {
    val nowMilestoneIndex = target.getNowMilestoneIndex()
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .padding(10.dp).verticalScroll(rememberScrollState())
    ) {
        for ((index, mile) in target.milestones.withIndex()) {
            CalMilestoneContent(target.startDay, mile, nowMilestoneIndex == index) {
                navController.navigate("calender/milestone/${target.fileName}/$index")
            }
        }
        Column {
            Row(modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = {
                        if (target.getDayAt() == 1) return@Button
                        target.quickDayAt--
                        val cal = Calendar.getInstance()
                        cal.time = target.startDay
                        cal.add(Calendar.DATE, 1)
                        target.startDay = cal.time
                        saveTarget(context, target)
                        navController.navigate(ScreenEnum.HOME.route)
                    },
                    colors = ButtonDefaults.run { buttonColors(Color(0xFF80A8FF)) },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("遅れているのでマイルストーンを巻き戻す")
                }
                Spacer(modifier = Modifier.width(10.dp))
                Button(
                    onClick = {
                        if (target.quickDayAt == target.endDayAt)return@Button
                        target.quickDayAt++
                        val cal = Calendar.getInstance()
                        cal.time = target.startDay
                        cal.add(Calendar.DATE, -1)
                        target.startDay = cal.time
                        saveTarget(context, target)
                        navController.navigate(ScreenEnum.HOME.route)
                    },
                    colors = ButtonDefaults.run { buttonColors(Color(0xFF80A8FF)) },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("進んでいるのでマイルストーンを進める")
                }
            }
            Button(
                onClick = { navController.navigateUp() },
                colors = ButtonDefaults.run { buttonColors(Color(0xFF80A8FF)) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("戻る")
            }
        }
    }
}

@SuppressLint("SimpleDateFormat")
@Composable
private fun CalMilestoneContent(startDay: Date, milestoneClass: MilestoneClass, isToday: Boolean = false, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Black.copy(
                alpha = 0f
            )
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = Color(if (isToday) 0xFFFF6D6D else 0xFF000000),
                    shape = RoundedCornerShape(size = 4.dp)
                )
                .padding(start = 4.dp, top = 4.dp, end = 4.dp, bottom = 4.dp)
        ) {
            Column {
                Text(
                    "Day${milestoneClass.dayAt}",
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF022859)
                    )
                )
                val calendar = Calendar.getInstance()
                calendar.time = startDay
                calendar.add(Calendar.DATE, milestoneClass.dayAt)
                val sdf = SimpleDateFormat("MM/dd")
                Text(
                    sdf.format(calendar.time),
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF022859)
                    )
                )
            }
            Text(
                text = milestoneClass.title,
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF000000)
                )
            )
            Text(
                text = ">",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF000000)
                ),
                modifier = Modifier.padding(4.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TargetCalenderScreenPreview() {
    SmallStrideTheme {
        TargetCalenderScreen(rememberNavController(), dummyTarget)
    }
}
