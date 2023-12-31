package dev.shiron.smallstride.view.screen

import android.annotation.SuppressLint
import android.util.Log
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import dev.shiron.smallstride.model.ReqTargetClass
import dev.shiron.smallstride.repository.callApi
import dev.shiron.smallstride.repository.saveWip
import dev.shiron.smallstride.ui.theme.SmallStrideTheme
import dev.shiron.smallstride.view.ScreenEnum
import java.util.Date

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TargetCreateScreen(navController: NavController, inputStr: String? = null, endDayAt: Int? = null, isBottom: Boolean = false) {
    var selectedNum by remember { mutableIntStateOf(endDayAt ?: 30) }
    val titleInput = remember { mutableStateOf(inputStr ?: "") }

    Scaffold {
        LazyColumn(contentPadding = it) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(
                        (if (isBottom) 10 else 100).dp,
                        Alignment.Top
                    ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "${selectedNum}日で達成したい目標を\n入力しましょう！",
                        style = TextStyle(
                            fontSize = 24.sp,
                            fontWeight = FontWeight(400),
                            color = Color(0xFF000000)
                        ),
                        textAlign = TextAlign.Center
                    )
                    Column(
                        // horizontalArrangement = Arrangement.spacedBy(0.dp, Alignment.Start),
                        // verticalAlignment = Alignment.CenterVertically,
                        // verticalArrangement = Arrangement.spacedBy(100.dp, Alignment.Top),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.Top),
                        modifier = Modifier
                            .padding(start = 30.dp, top = 0.dp, end = 30.dp, bottom = 0.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "目標達成までの期間")
                            DayDropDownMenu(selectedNum) {
                                selectedNum = it
                            }
                        }

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(0.dp, Alignment.Start),
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .border(
                                    width = 1.dp,
                                    color = Color(0xFF7E7E7E),
                                    shape = RoundedCornerShape(size = 12.dp)
                                )
                                .background(
                                    color = Color(0xFFFFFFFF),
                                    shape = RoundedCornerShape(size = 12.dp)
                                )
                                .padding(start = 10.dp, top = 10.dp, end = 10.dp, bottom = 10.dp)
                                .height(20.dp)
                        ) {
                            BasicTextField(
                                modifier = Modifier.fillMaxSize(),
                                value = titleInput.value,
                                singleLine = true,
                                onValueChange = { titleInput.value = it },
                                decorationBox = { innerTextField ->
                                    Box(modifier = Modifier.fillMaxSize()) {
                                        if (titleInput.value.isEmpty()) {
                                            Text(text = "目標を入力")
                                        }
                                        innerTextField()
                                    }
                                }
                            )
                        }
                    }
                    CreateButton(navController, titleInput, selectedNum)
                }
            }
        }
    }
}

@Composable
fun CreateButton(navController: NavController, titleInput: MutableState<String>, selectedNum: Int) {
    Button(
        onClick = {
            if (titleInput.value.isEmpty())return@Button
            val reqTarget = ReqTargetClass(
                title = titleInput.value,
                startDay = Date(),
                endDayAt = selectedNum,
                marginQuickDayAt = 0
            )
            callApi(reqTarget, onFailure = {
                Log.d("callApi", it.toString())
            }) {
                if (it == null) {
                    navController.navigate(ScreenEnum.TARGET_CREATE.route)
                    return@callApi
                }

                saveWip(it)
                navController.navigate("target/result/${it.fileName}") {
                    popUpTo(ScreenEnum.NOW_LOADING.route) {
                        // 指定の目的地（screen2）も含む
                        inclusive = true
                    }
                }
            }

            navController.navigate("nowloading")
        },
        colors = ButtonDefaults.run { buttonColors(FloatingActionButtonDefaults.containerColor) },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            "作成",
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight(400),
                color = Color.DarkGray
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DayDropDownMenu(selectedNum: Int, onClick: (item: Int) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val items = listOf(30, 60, 90)

    Box(
        modifier = Modifier
            .wrapContentSize(Alignment.TopEnd)
            .width(130.dp)
            .padding(start = 10.dp, top = 0.dp, end = 10.dp, bottom = 0.dp)
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            OutlinedTextField(
                value = "${selectedNum}日",
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor(),
                shape = RoundedCornerShape(8.dp),
                singleLine = true
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                for (item in items) {
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = "${item}日",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight(400),
                                    color = Color(0xFF000000)
                                )
                            )
                        },
                        onClick = {
                            onClick(item)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NewTargetScreenPreview() {
    SmallStrideTheme {
        TargetCreateScreen(rememberNavController())
    }
}
