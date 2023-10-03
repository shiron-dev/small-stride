package dev.shiron.smallstride

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import dev.shiron.smallstride.domain.MilestoneClass
import dev.shiron.smallstride.domain.TargetClass
import dev.shiron.smallstride.ui.theme.SmallStrideTheme
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun targetGenResultScreen(target:TargetClass,navController: NavController) {
    val scrollState = rememberScrollState()

    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 20.dp, top = 20.dp, end = 20.dp, bottom = 20.dp)
    ) {
        Text(
            text = "${target.endDayAt}日で${target.title}\n",
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight(400),
                color = Color(0xFF000000),
            )
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.verticalScroll(scrollState)
        ) {
            for (milestone in target.milestones) {
                milestoneContent(target.startDay, milestone)
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(0.dp, Alignment.Top),
                modifier = Modifier
                    .padding(10.dp)
            ) {
                Button(
                    onClick = { /*TODO*/ },
                    colors = ButtonDefaults.run { buttonColors(Color(0xFF80A8FF)) },
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text("変更")
                }
                Button(
                    onClick = { navController.navigate("main") },
                    colors = ButtonDefaults.run { buttonColors(Color(0xFF80A8FF)) },
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text("登録")
                }
            }
        }
    }
}

@Composable
fun milestoneContent(startDay:Date, milestoneClass: MilestoneClass) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.Start),
        verticalAlignment = Alignment.Top,
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = Color(0xFF000000),
                shape = RoundedCornerShape(size = 4.dp)
            )
            .padding(start = 4.dp, top = 4.dp, end = 4.dp, bottom = 4.dp)
    ) {
        Column {
            Text("Day${milestoneClass.dayAt}")
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
                color = Color(0xFF000000),
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun targetGenResultScreenPreview() {
    SmallStrideTheme {
        targetGenResultScreen(targetObj, rememberNavController())
    }
}