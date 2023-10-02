package dev.shiron.smallstride

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.zIndex
import dev.shiron.smallstride.domain.MilestoneClass
import dev.shiron.smallstride.ui.theme.SmallStrideTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SmallStrideTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    HomeScreen()
                }
            }
        }
    }
}

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
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
            NewMilestone()
            MilestoneList(
                listOf(
                    MilestoneClass(
                        title = "マイルストーン1",
                        description = "マイルストーン1の説明",
                        startDay = java.util.Date(),
                        endDayAt = 30,
                        milestones = listOf(
                            dev.shiron.smallstride.domain.SmallMilestone(
                                title = "マイルストーン1-1",
                                hint = "マイルストーン1-1のヒント",
                                dayAt = 1
                            ),
                            dev.shiron.smallstride.domain.SmallMilestone(
                                title = "マイルストーン1-2",
                                hint = "マイルストーン1-2のヒント",
                                dayAt = 2
                            ),
                            dev.shiron.smallstride.domain.SmallMilestone(
                                title = "マイルストーン1-3",
                                hint = "マイルストーン1-3のヒント",
                                dayAt = 3
                            ),
                        )
                    )
                )
            )
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(
                onClick = { /*TODO*/ },
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
        }
    }
}

@Composable
fun MilestoneList(milestoneList: List<MilestoneClass>,modifier: Modifier = Modifier){
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top),
        horizontalAlignment = Alignment.Start,
    ) {
        for (milestone in milestoneList) {
            Milestone(milestone = milestone)
        }
    }
}

@Composable
fun Milestone(milestone:MilestoneClass, modifier: Modifier = Modifier) {
    Button(
        onClick = { /*TODO*/ },
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
                        text = milestone.title,
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
                )
            )
        }
    }
}

@Composable
fun NewMilestone(modifier: Modifier = Modifier) {
    Button(
        onClick = { /*TODO*/ },
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
                text = "新しいマイルストーンを設定する",
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
    SmallStrideTheme {
        HomeScreen()
    }
}