package dev.shiron.smallstride.view

import dev.shiron.smallstride.model.MilestoneClass
import dev.shiron.smallstride.model.TargetClass
import java.util.Date

val dummyTarget =
    TargetClass(
        title = "マイルストーン1",
        input = "マイルストーン",
        startDay = Date(),
        endDayAt = 30,
        quickDayAt = 0,
        fileName = "test.json",
        milestones = listOf(
            MilestoneClass(
                title = "マイルストーン1-1",
                hint = "マイルストーン1-1のヒント",
                dayAt = 10
            ),
            MilestoneClass(
                title = "マイルストーンのマイルストーン1-2",
                hint = "マイルストーン1-2のヒント",
                dayAt = 20
            ),
            MilestoneClass(
                title = "mile 1-3",
                hint = "マイルストーン1-3のヒント",
                dayAt = 30
            )
        )
    )
