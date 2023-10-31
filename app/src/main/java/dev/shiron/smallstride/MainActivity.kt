package dev.shiron.smallstride

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.shiron.smallstride.model.MilestoneClass
import dev.shiron.smallstride.model.TargetClass
import dev.shiron.smallstride.repository.loadAllTarget
import dev.shiron.smallstride.ui.theme.SmallStrideTheme
import dev.shiron.smallstride.view.screen.AllCalenderScreen
import dev.shiron.smallstride.view.screen.HomeScreen
import dev.shiron.smallstride.view.screen.MilestoneCalenderScreen
import dev.shiron.smallstride.view.screen.NewTargetScreen
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
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "main") {
                        composable("main") {
                            HomeScreen(navController = navController, loadAllTarget(LocalContext.current))
                        }
                        composable(
                            "newtarget/new"
                        ) {
                            NewTargetScreen(navController = navController)
                        }
                        composable(
                            "nowloading"
                        ) {
                            NowLoadingScreen()
                        }
                        composable("newtarget/result") {
                            if (tmpTarget != null) {
                                TargetGenResultScreen(navController, tmpTarget!!)
                            } else {
                                tmpTarget = null
                                tmpMilestone = null
                                navController.navigate("main")
                            }
                        }
                        composable("calender/all") {
                            AllCalenderScreen(navController, loadAllTarget(LocalContext.current))
                        }
                        composable("calender/target") {
                            if (tmpTarget != null) {
                                TargetCalenderScreen(navController, tmpTarget!!)
                            } else {
                                tmpTarget = null
                                tmpMilestone = null
                                navController.navigate("main")
                            }
                        }
                        composable("calender/milestone") {
                            if (tmpMilestone != null && tmpTarget != null) {
                                MilestoneCalenderScreen(navController, tmpTarget!!, tmpMilestone!!)
                            } else {
                                tmpTarget = null
                                tmpMilestone = null
                                navController.navigate("main")
                            }
                        }
                    }
                }
            }
        }
    }
}
