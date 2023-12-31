package dev.shiron.smallstride.model

import java.io.Serializable
import java.util.Date

data class TargetClass(
    var title: String,
    var input: String,
    var startDay: Date,
    var milestones: List<MilestoneClass>,
    var endDayAt: Int,
    var quickDayAt: Int,
    var fileName: String
) : Serializable {
    fun getDayAt(): Int {
        val now = Date()
        val diffTime = now.time - startDay.time
        val diffDay = diffTime / (1000 * 60 * 60 * 24)
        return diffDay.toInt() + 1
    }

    fun getNowMilestoneIndex(): Int {
        val dayAt = getDayAt()
        var min = Int.MAX_VALUE
        var ret = 0
        for ((index, milestone) in milestones.withIndex()) {
            if (dayAt >= milestone.dayAt && dayAt - milestone.dayAt < min) {
                min = dayAt - milestone.dayAt
                ret = index
            }
        }
        return ret
    }
}

data class ReqTargetClass(
    var title: String,
    var startDay: Date,
    var endDayAt: Int,
    var marginQuickDayAt: Int
)
