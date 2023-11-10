package dev.shiron.smallstride

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dev.shiron.smallstride.model.MilestoneClass
import dev.shiron.smallstride.model.TargetClass
import dev.shiron.smallstride.repository.loadAllTarget
import dev.shiron.smallstride.repository.readWip
import dev.shiron.smallstride.ui.theme.SmallStrideTheme
import dev.shiron.smallstride.view.ScreenEnum
import dev.shiron.smallstride.view.screen.AllCalenderScreen
import dev.shiron.smallstride.view.screen.HomeScreen
import dev.shiron.smallstride.view.screen.MilestoneCalenderScreen
import dev.shiron.smallstride.view.screen.TargetCreateScreen
import dev.shiron.smallstride.view.screen.NowLoadingScreen
import dev.shiron.smallstride.view.screen.TargetCalenderScreen
import dev.shiron.smallstride.view.screen.TargetGenResultScreen

var tmpTarget: TargetClass? = null
var tmpMilestone: MilestoneClass? = null

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = intent
        val deepLinkUri = intent.data

        if (deepLinkUri != null) {
            val deepLinkData = deepLinkUri.toString()

            println(deepLinkData)
        }

        setContent {
            SmallStrideTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Routes()
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Routes() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = ScreenEnum.HOME.route) {
        composable(ScreenEnum.HOME.route) {
            HomeScreen(navController = navController, loadAllTarget(LocalContext.current))
        }
        composable(
            ScreenEnum.TARGET_CREATE.route
        ) {
            TargetCreateScreen(navController = navController)
        }
        composable(
            ScreenEnum.TARGET_CREATE.route,
            arguments = listOf(navArgument("inputStr") { type = NavType.StringType })
        ) {
            val inputStr = it.arguments?.getString("inputStr") ?: run {
                TargetCreateScreen(navController = navController)
                return@composable
            }
            if(inputStr == "{inputStr}")
                TargetCreateScreen(navController = navController)
            else
                TargetCreateScreen(navController = navController, inputStr = inputStr)
        }
        composable(
            ScreenEnum.NOW_LOADING.route
        ) {
            NowLoadingScreen()
        }
        composable(
            route = ScreenEnum.TARGET_RESULT.route,
            arguments = listOf(navArgument("target") { type = NavType.StringType })
        ) {
            val targetID = it.arguments?.getString("target") ?: run {
                navController.navigate(ScreenEnum.TARGET_CREATE.route)
                return@composable
            }
            val target = readWip(targetID) ?: run {
                navController.navigate(ScreenEnum.TARGET_CREATE.route)
                return@composable
            }
            TargetGenResultScreen(navController, target)
        }
        composable(ScreenEnum.ALL_CALENDER.route) {
            AllCalenderScreen(navController, loadAllTarget(LocalContext.current))
        }
        composable(ScreenEnum.TARGET_CALENDER.route) {
            if (tmpTarget != null) {
                TargetCalenderScreen(navController, tmpTarget!!)
            } else {
                tmpTarget = null
                tmpMilestone = null
                navController.navigate(ScreenEnum.HOME.route)
            }
        }
        composable(ScreenEnum.MILESTONE_CALENDER.route) {
            if (tmpMilestone != null && tmpTarget != null) {
                MilestoneCalenderScreen(navController, tmpTarget!!, tmpMilestone!!)
            } else {
                tmpTarget = null
                tmpMilestone = null
                navController.navigate(ScreenEnum.HOME.route)
            }
        }
    }
}
