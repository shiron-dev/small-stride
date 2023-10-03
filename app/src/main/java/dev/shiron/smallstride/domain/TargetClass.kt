package dev.shiron.smallstride.domain

import java.util.Date

data class TargetClass (
    var title: String,
    var startDay: Date,
    var milestones: List<MilestoneClass>,
    var endDayAt: Int,
    var quickDayAt: Int,
){
    fun getDayAt(): Int {
        val now = Date()
        val diffTime = now.time - startDay.time
        val diffDay = diffTime / (1000 * 60 * 60 * 24)
        return diffDay.toInt() + 1
    }

    fun getNowMilestone(): MilestoneClass? {
        val dayAt = getDayAt()
        var min = Int.MAX_VALUE
        var ret:MilestoneClass? = null
        for (milestone in milestones) {
            if (dayAt >= milestone.dayAt && dayAt - milestone.dayAt < min) {
                min = dayAt - milestone.dayAt
                ret = milestone
            }
        }
        return ret
    }
}

data class MilestoneClass (
    var title: String,
    var hint: String,
    var dayAt: Int
)

data class ReqTargetClass(
    var title: String,
    var startDay: Date,
    var endDayAt: Int,
    var marginQuickDayAt: Int
)