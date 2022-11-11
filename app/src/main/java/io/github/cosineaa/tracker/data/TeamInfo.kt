package io.github.cosineaa.tracker.data

data class TeamInfo(
    val teamName: String,
    val teamImage: String,

    val gameRate: String,
    val gameAll: String,
    val gameWin: String,
    val gameLose: String,
    val gameContinuity: String,

    val homeWin: String,
    val homeLose: String,
    val awayWin: String,
    val awayLose: String,

    val conference: String,
    val conferenceRank: String
) : Comparable<TeamInfo> {
    override fun compareTo(other: TeamInfo): Int {
        return compareValuesBy(this, other) { (it.conferenceRank).toInt() }
    }
}
