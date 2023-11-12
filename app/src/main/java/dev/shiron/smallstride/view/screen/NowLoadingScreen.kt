package dev.shiron.smallstride.view.screen

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.shiron.smallstride.repository.callTimeApi
import dev.shiron.smallstride.ui.theme.SmallStrideTheme

@Composable
fun NowLoadingScreen() {
    BackHandler(enabled = true) { }

    var time by remember {
        mutableIntStateOf(35)
    }
    if (time == 35) {
        callTimeApi(onFailure = {
            Log.d("callApi", it.toString())
        }) {
            if (it != null) {
                time = it
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
        Text("マイルストーンを生成しています...")
        Text("現在の平均処理時間は${time}秒です")
    }
}

@Preview(showBackground = true)
@Composable
fun NowLoadingScreenPreview() {
    SmallStrideTheme {
        NowLoadingScreen()
    }
}
