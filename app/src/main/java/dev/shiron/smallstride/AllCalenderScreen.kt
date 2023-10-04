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
import androidx.compose.ui.graphics.Color
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
fun allCalenderScreen(navController: NavController,targets:List<TargetClass>) {

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 20.dp, top = 20.dp, end = 20.dp, bottom = 20.dp)
    ) {
        Text(
            "全体マイルストーン確認",
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight(400),
                color = Color(0xFF000000),
            )
        )
        milestonesList(navController,targets)
        Button(
            onClick = { navController.navigate("home") },
            colors = ButtonDefaults.run { ButtonDefaults.buttonColors(Color(0xFF80A8FF)) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("ホームに戻る")
        }
    }
}

@Composable
private fun milestonesList(navController: NavController,targets: List<TargetClass>) {
    val milestones = mutableListOf<Pair<Date, Pair<TargetClass, MilestoneClass>>>()
    for (target in targets) {
        for (milestone in target.milestones) {
            val calender = Calendar.getInstance()
            calender.time = target.startDay
            calender.add(Calendar.DATE, milestone.dayAt)
            milestones.add(Pair(calender.time, Pair(target, milestone)))
        }
    }
    milestones.sortBy { it.first.time }

    Column (
        modifier = Modifier
            .padding(10.dp).verticalScroll(rememberScrollState())
    ){
        var lastDate = Date(0)
        var miles = mutableListOf<Pair<TargetClass,MilestoneClass>>()
        for (milestone in milestones) {
            if (milestone.first != lastDate) {
                if (miles.isNotEmpty()) {
                    milestoneList(navController,date = lastDate, miles = miles)
                }
                miles = mutableListOf()
                lastDate = milestone.first
            }
            miles.add(milestone.second)
        }
        milestoneList(navController,date = lastDate, miles = miles)
    }
}

@Composable
private fun milestoneList(navController: NavController,date:Date,miles:List<Pair<TargetClass,MilestoneClass>>) {
    Row (
        modifier = Modifier.padding(10.dp)
    ){
        val sdf = SimpleDateFormat("MM/dd")
        Text(
            text = sdf.format(date),
            modifier = Modifier.padding(end = 20.dp),
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight(400),
                color = Color(0xFF000000),
            )
        )
        Column (modifier = Modifier.fillMaxWidth()){
            for (mile in miles) {
                calMilestoneContent(date, mile.first, mile.second){
                    tmpTarget = mile.first
                }
            }
        }
    }
}

@Composable
private fun calMilestoneContent(date: Date, targetClass:TargetClass,milestone: MilestoneClass, onClick: () -> Unit) {

    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Black.copy(
                alpha = 0f,
            ),
        ),
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = Color(0xFF022859),
                    shape = RoundedCornerShape(size = 8.dp)
                )
                .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 8.dp))
                .fillMaxSize()
        ) {
            Row {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        progress = milestone.dayAt.toFloat() / targetClass.endDayAt.toFloat(),
                        color = Color(0xFF8395F9)
                    )
                    Text("Day${milestone.dayAt}", color = Color(0xFF022859))
                }
                Column(
                    verticalArrangement = Arrangement.spacedBy(0.dp, Alignment.Top),
                    horizontalAlignment = Alignment.Start,
                ) {
                    Text(
                        text = milestone.title,
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight(400),
                            color = Color(0xFF022859),
                        )
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(0.dp, Alignment.Start),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(start = 10.dp)
                    ) {
                        Text(
                            text = milestone.title,
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight(400),
                                color = Color(0xFF022859),
                            )
                        )
                    }
                }
            }
            Column {
                Text(
                    text = ">",
                    style = TextStyle(
                        fontSize = 32.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF022859)
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun allCalenderScreenPreview() {
    val target2 = targetObj.copy(title = "マイルストーン2!!")
    SmallStrideTheme {
        allCalenderScreen(rememberNavController(),listOf(targetObj, target2))
    }
}