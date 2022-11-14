package io.github.cosineaa.tracker.data

data class TeamInfo(
    val teamKoreanName: String,
    val teamEnglishName: String,
    val teamKoreanShortName: String,
    val teamEnglishShortName: String,
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

    val ppg: String, // 득점
    val oppg: String, // 실점
    val rpg: String, // 리바운드
    val apg: String, // 어시스트

    val conference: String,
    val conferenceRank: String
) : Comparable<TeamInfo>, java.io.Serializable {

    override fun compareTo(other: TeamInfo): Int {
        return compareValuesBy(this, other) { (it.conferenceRank).toInt() }
    }
}
