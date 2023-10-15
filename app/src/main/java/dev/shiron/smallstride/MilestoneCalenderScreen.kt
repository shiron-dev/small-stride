package dev.shiron.smallstride

import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import dev.shiron.smallstride.domain.MilestoneClass
import dev.shiron.smallstride.domain.TargetClass
import dev.shiron.smallstride.ui.theme.SmallStrideTheme
import java.text.SimpleDateFormat
import java.util.Calendar

@SuppressLint("SimpleDateFormat")
@Composable
fun MilestoneCalenderScreen(navController: NavController, target: TargetClass, milestone: MilestoneClass) {

    var expanded by remember { mutableStateOf(false) }

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
                color = Color(0xFF000000),
            )
        )
        Column(verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.Top)) {
            Row(horizontalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterHorizontally)) {
                Text(
                    "Day${milestone.dayAt}",
                    style = TextStyle(
                        fontSize = 22.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF000000),
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
                        color = Color(0xFF000000),
                    )
                )
            }
            Text(
                milestone.title,
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF000000),
                )
            )
            Button(
                onClick = { expanded = !expanded },
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = Color(0xFF022859),
                        shape = RoundedCornerShape(size = 8.dp)
                    ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White.copy(
                        alpha = 0f,
                    ),
                ),
            ) {
                Column {
                    Text(
                        text = "Hintを${if(expanded) "閉じる" else "開く" }",
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight(400),
                            color = Color(0xFF000000),
                        )
                    )
                    if (expanded) {
                        Text(
                            text = milestone.hint,
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight(400),
                                color = Color(0xFF000000),
                            )
                        )
                    }
                }
            }
        }
        Column {
            Button(
                onClick = {
                    tmpTarget = target
                    navController.navigate("calender/target")
                },
                colors = ButtonDefaults.run { buttonColors(Color(0xFF80A8FF)) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("目標の予定へ")
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

@Preview(showBackground = true)
@Composable
fun MilestoneCalenderScreen() {
    SmallStrideTheme {
        MilestoneCalenderScreen(rememberNavController(), targetObj, targetObj.milestones.first())
    }
}