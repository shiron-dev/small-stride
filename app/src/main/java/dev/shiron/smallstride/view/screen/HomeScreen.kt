package dev.shiron.smallstride.view.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.shiron.smallstride.model.MilestoneClass
import dev.shiron.smallstride.model.TargetClass
import dev.shiron.smallstride.ui.theme.SmallStrideTheme
import dev.shiron.smallstride.view.component.MyScaffold
import dev.shiron.smallstride.view.component.ProgressMilestone
import dev.shiron.smallstride.view.dummyTarget

@Composable
fun HomeScreen(navController: NavController, targets: List<TargetClass>) {
    MyScaffold(navController = navController, "現在進行中のマイルストーン") {
        LazyColumn(
            contentPadding = it,
            modifier = Modifier.padding(start = 20.dp, end = 20.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top)
        ) {
            val milestoneList = targets.filter { it1 -> it1.getDayAt() <= it1.endDayAt }
            items(milestoneList.size) { it1 ->
                ProgressMilestone(target = milestoneList[it1], milestoneIndex = milestoneList[it1].getNowMilestoneIndex()) {
                    navController.navigate("calender/target/${milestoneList[it1].fileName}")
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
