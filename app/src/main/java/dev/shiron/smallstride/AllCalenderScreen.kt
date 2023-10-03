package dev.shiron.smallstride

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
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
        milestonesList(targets)
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
fun milestonesList(targets: List<TargetClass>) {
    val milestones = mutableListOf<Pair<Date, MilestoneClass>>()
    for (target in targets) {
        for (milestone in target.milestones) {
            val calender = Calendar.getInstance()
            calender.time = target.startDay
            calender.add(Calendar.DATE, milestone.dayAt)
            milestones.add(Pair(calender.time, milestone))
        }
    }
    milestones.sortBy { it.first.time }

    Column (
        modifier = Modifier
            .padding(10.dp).verticalScroll(rememberScrollState())
    ){
        var lastDate = Date(0)
        var miles = mutableListOf<MilestoneClass>()
        for (milestone in milestones) {
            if (milestone.first != lastDate) {
                if (miles.isNotEmpty()) {
                    milestoneList(date = lastDate, miles = miles)
                }
                miles = mutableListOf<MilestoneClass>()
                lastDate = milestone.first
            }
            miles.add(milestone.second)
        }
        milestoneList(date = lastDate, miles = miles)
    }
}

@Composable
fun milestoneList(date:Date,miles:List<MilestoneClass>) {
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
        Column {
            for (mile in miles) {
                milestoneContent(date, mile)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun targetCalenderScreenPreview() {
    val target2 = targetObj.copy(title = "マイルストーン2!!")
    SmallStrideTheme {
        allCalenderScreen(rememberNavController(),listOf(targetObj, target2))
    }
}