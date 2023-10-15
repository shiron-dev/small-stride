package dev.shiron.smallstride

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun InvisibleButton(onClick: () -> Unit, modifier: Modifier = Modifier, content: @Composable() (RowScope.() -> Unit)) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White.copy(
                alpha = 0f,
            ),
        ),
    ){
        content()
    }
}