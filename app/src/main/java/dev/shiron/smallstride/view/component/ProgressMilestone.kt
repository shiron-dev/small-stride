package dev.shiron.smallstride.view.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.shiron.smallstride.model.TargetClass
import dev.shiron.smallstride.view.dummyTarget

@Composable
fun ProgressMilestone(target: TargetClass, milestoneIndex: Int, onClick: () -> Unit) {
    val milestone = target.milestones[milestoneIndex]
    OutlinedButton(
        onClick = onClick,
        shape = RoundedCornerShape(size = 8.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = Color.Black
        )
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        progress = milestone.dayAt.toFloat() / target.endDayAt.toFloat(),
                        color = Color(0xFFd1d8fd)
                    )
                    Text("${milestone.dayAt}日目", style = MaterialTheme.typography.bodyMedium)
                }
                Column(
                    horizontalAlignment = Alignment.Start
                    // modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "${target.endDayAt}日で${target.title}",
                        style = MaterialTheme.typography.titleSmall,
                        // odifier = Modifier.fillMaxWidth(),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(0.dp, Alignment.Start),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(start = 20.dp)
                        // .fillMaxWidth()
                    ) {
                        Text(
                            text = milestone.title,
                            style = MaterialTheme.typography.bodyMedium,
                            // modifier = Modifier.fillMaxWidth(),
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )
                    }
                }
            }
            Box {
                Icon(imageVector = Icons.Filled.KeyboardArrowRight, contentDescription = null)
            }
        }
    }
}

@Preview
@Composable
fun ProgressMilestonePreview() {
    ProgressMilestone(
        target = dummyTarget,
        milestoneIndex = 0,
        onClick = {}
    )
}
