package dev.shiron.smallstride.view.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.shiron.smallstride.ui.theme.SmallStrideTheme

@Composable
fun NowLoadingScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
        Text("マイルストーンを生成しています...")
    }
}

@Preview(showBackground = true)
@Composable
fun NowLoadingScreenPreview() {
    SmallStrideTheme {
        NowLoadingScreen()
    }
}