package dev.shiron.smallstride

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.shiron.smallstride.domain.MilestoneClass
import dev.shiron.smallstride.domain.TargetClass
import dev.shiron.smallstride.ui.theme.SmallStrideTheme
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

@Composable
fun targetCalenderScreen(navController: NavController,target: TargetClass) {

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
                color = Color(0xFF000000),
            )
        )
        milestonesList(navController,target)
    }
}

@Composable
private fun milestonesList(navController:NavController,target: TargetClass) {
    val nowMilestone = target.getNowMilestone()
    val context = LocalContext.current
    Column (
        modifier = Modifier
            .padding(10.dp).verticalScroll(rememberScrollState())
    ){
        for (mile in target.milestones) {
            calMilestoneContent(target.startDay, mile, nowMilestone == mile) {
                tmpTarget = target
                tmpMilestone = mile
                navController.navigate("calender/milestone")
            }
        }
        Column {
            Row(modifier=Modifier.fillMaxWidth()){
                Button(
                    onClick = {
                        target.quickDayAt--
                        val cal = Calendar.getInstance()
                        cal.time = target.startDay
                        cal.add(Calendar.DATE, 1)
                        target.startDay = cal.time
                        saveTarget(context, target)
                        navController.navigate("main")
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
                          target.quickDayAt++
                        val cal = Calendar.getInstance()
                        cal.time = target.startDay
                        cal.add(Calendar.DATE, -1)
                        target.startDay = cal.time
                        saveTarget(context, target)
                        navController.navigate("main")
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

@Composable
private fun calMilestoneContent(startDay: Date, milestoneClass: MilestoneClass, isToday: Boolean =false, onClick: () -> Unit){
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Black.copy(
                alpha = 0f,
            ),
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
                    color = Color(if(isToday) 0xFFFF6D6D else 0xFF000000),
                    shape = RoundedCornerShape(size = 4.dp)
                )
                .padding(start = 4.dp, top = 4.dp, end = 4.dp, bottom = 4.dp)
        ) {
            Column {
                Text("Day${milestoneClass.dayAt}",
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF022859),
                    ))
                val calendar = Calendar.getInstance()
                calendar.time = startDay
                calendar.add(Calendar.DATE, milestoneClass.dayAt)
                val sdf = SimpleDateFormat("MM/dd")
                Text(sdf.format(calendar.time),
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF022859),
                    ))
            }
            Text(
                text = milestoneClass.title,
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF000000),
                )
            )
            Text(
                text = ">",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF000000),
                ),
                modifier = Modifier.padding(4.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun targetCalenderScreenPreview() {
    SmallStrideTheme {
        targetCalenderScreen(rememberNavController(), targetObj)
    }
}