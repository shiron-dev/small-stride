package dev.shiron.smallstride.view.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.shiron.smallstride.model.TargetClass
import dev.shiron.smallstride.targetObj
import dev.shiron.smallstride.tmpMilestone
import dev.shiron.smallstride.tmpTarget
import dev.shiron.smallstride.ui.theme.SmallStrideTheme


@Composable
fun HomeScreen(navController: NavController, targets: List<TargetClass>){
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 20.dp, top = 20.dp, end = 20.dp, bottom = 20.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // Child views.
            NewTargetButton {
                tmpTarget = null
                tmpMilestone = null
                navController.navigate("newtarget/new")
            }
            MilestoneList(targets, navController)
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
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
                        textAlign = TextAlign.Center,
                    )
                )
            }
            /*
            Button(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.run { buttonColors(Color(0xFF80A8FF)) },
                modifier = Modifier.fillMaxWidth()

            ) {
                Text(
                    text = "設定画面へ",
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight(500),
                        color = Color(0xFFFFFFFF),
                        textAlign = TextAlign.Center,
                    )
                )
            }

             */
        }
    }
}

@Composable
fun MilestoneList(milestoneList: List<TargetClass>, navController: NavController){
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top),
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        for (milestone in milestoneList) {
            if(milestone.getDayAt() > milestone.endDayAt) continue
            Milestone(milestone = milestone){
                tmpTarget = milestone
                navController.navigate("calender/target")
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
                alpha = 0f,
            ),
        ),
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
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
            Row(
                verticalAlignment = Alignment.CenterVertically
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
                    verticalArrangement = Arrangement.spacedBy(0.dp, Alignment.Top),
                    horizontalAlignment = Alignment.Start,
                ) {
                    Text(
                        text = "${milestone.endDayAt}日で${milestone.title}",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight(400),
                            color = Color(0xFF022859),
                        )
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(0.dp, Alignment.Start),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(start = 20.dp)
                    ) {
                        Text(
                            text = milestone.getNowMilestone()?.title ?: "",
                            style = TextStyle(
                                fontSize = 24.sp,
                                fontWeight = FontWeight(400),
                                color = Color(0xFF022859),
                            )
                        )
                    }
                }
            }
            Text(
                text = ">",
                style = TextStyle(
                    fontSize = 32.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF022859),
                    textAlign = TextAlign.Center,
                ),
                modifier = Modifier.padding(4.dp)
            )
        }
    }
}

@Composable
fun NewTargetButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Black.copy(
                alpha = 0f,
            ),
        ),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = Color(0xFF7E7E7E),
                    shape = RoundedCornerShape(size = 12.dp)
                )
                .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 12.dp))
                .padding(start = 10.dp, top = 10.dp, end = 10.dp, bottom = 10.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "新しい目標を設定する",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF7B7B7B),
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    val target2 = targetObj.copy(title = "マイルストーン2!!")
    SmallStrideTheme {
        HomeScreen(navController = rememberNavController(),listOf(targetObj,target2))
    }
}
