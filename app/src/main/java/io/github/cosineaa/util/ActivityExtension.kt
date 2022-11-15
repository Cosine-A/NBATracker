package io.github.cosineaa.util

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import io.github.cosineaa.MainActivity.Companion.dpi
import io.github.cosineaa.tracker.data.TeamInfo
import kotlin.math.round

fun String.getStat(): String {
    return String.format("%.1f", this.toDouble())
}

fun Int.size(): Dp {
    val size = this.toFloat()
    return if (dpi <= 120) {
        Dp(round(size / 4))
    }
    else if (dpi <= 160) {
        Dp(round(size / 3))
    }
    else if (dpi <= 213) {
        Dp(round((size / 2.253).toFloat()))
    }
    else if (dpi <= 240) {
        Dp(round(size / 2))
    }
    else if (dpi <= 320) {
        Dp(round((size / 1.5).toFloat()))
    }
    else if (dpi <= 480) {
        Dp(size)
    }
    else {
        Dp(round((size * 1.333).toFloat()))
    }
}

fun auto(name: String): TextUnit {
    return if (name.contains("포틀랜드")) 20.sp else 24.sp
}

fun List<TeamInfo>.getPointsPerGameRank(team: String): String {
    val ppgRanks = sortedBy { it.ppg }.reversed()
    ppgRanks.forEach {
        if (it.teamKoreanShortName == team) {
            return (ppgRanks.indexOf(it) + 1).toString()
        }
    }
    return "-1"
}
fun List<TeamInfo>.getOpponentPointsPerGameRank(team: String): String {
    val ppgRanks = sortedBy { it.oppg }
    ppgRanks.forEach {
        if (it.teamKoreanShortName == team) {
            return (ppgRanks.indexOf(it) + 1).toString()
        }
    }
    return "-1"
}
fun List<TeamInfo>.getReboundsPerGameRank(team: String): String {
    val ppgRanks = sortedBy { it.rpg }.reversed()
    ppgRanks.forEach {
        if (it.teamKoreanShortName == team) {
            return (ppgRanks.indexOf(it) + 1).toString()
        }
    }
    return "-1"
}
fun List<TeamInfo>.getAssistsPerGameRank(team: String): String {
    val ppgRanks = sortedBy { it.apg }.reversed()
    ppgRanks.forEach {
        if (it.teamKoreanShortName == team) {
            return (ppgRanks.indexOf(it) + 1).toString()
        }
    }
    return "-1"
}

