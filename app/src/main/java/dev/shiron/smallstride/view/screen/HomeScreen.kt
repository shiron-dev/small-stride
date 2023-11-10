package dev.shiron.smallstride.view.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import dev.shiron.smallstride.ui.theme.SmallStrideTheme
import dev.shiron.smallstride.view.component.MyScaffold
import dev.shiron.smallstride.view.dummyTarget

@Composable
fun HomeScreen(navController: NavController, targets: List<TargetClass>) {
    MyScaffold(navController = navController, "Home") {
        LazyColumn(contentPadding = it) {
            val milestoneList = targets.filter { it1 -> it1.getDayAt() <= it1.endDayAt }
            items(milestoneList.size) { it1 ->
                Milestone(milestone = milestoneList[it1]) {
                    navController.navigate("calender/target/${milestoneList[it1].fileName}")
                }
            }
        }
    }
}

@Composable
fun Milestone(milestone: TargetClass, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Black.copy(
                alpha = 0f
            )
        )
    ) {
        Row(
            // horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = Color(0xFF022859),
                    shape = RoundedCornerShape(size = 8.dp)
                )
                .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 8.dp))
                .padding(start = 3.dp, top = 3.dp, end = 3.dp, bottom = 3.dp)
                .fillMaxWidth()
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    progress = milestone.getDayAt().toFloat() / milestone.endDayAt.toFloat(),
                    color = Color(0xFF8395F9)
                )
                Text("Day${milestone.getDayAt()}", color = Color(0xFF022859))
            }
            Column(
                // verticalArrangement = Arrangement.spacedBy(0.dp, Alignment.Top),
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "${milestone.endDayAt}日で${milestone.title}",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF022859)
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(0.dp, Alignment.Start),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(start = 20.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = milestone.getNowMilestone().title,
                        style = TextStyle(
                            fontSize = 24.sp,
                            fontWeight = FontWeight(400),
                            color = Color(0xFF022859)
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    val target2 = dummyTarget.copy(title = "マイルストーン2!!", milestones = listOf(MilestoneClass("長めのマイルストーンのタイトルです。すごく長いです。", "hoge", 2)))
    SmallStrideTheme {
        HomeScreen(navController = rememberNavController(), listOf(dummyTarget, target2))
    }
}
