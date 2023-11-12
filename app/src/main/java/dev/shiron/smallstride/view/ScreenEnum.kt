package dev.shiron.smallstride.view

enum class ScreenEnum(val route: String) {
    HOME("home"),
    TARGET_CREATE("target/create"),
    NOW_LOADING("nowloading"),
    TARGET_RESULT("target/result/{target}"),
    TARGET_CALENDER("calender/target/{target}"),
    MILESTONE_CALENDER("calender/milestone/{target}/{milestone}"),
    ALL_CALENDER("calender/all")
}
