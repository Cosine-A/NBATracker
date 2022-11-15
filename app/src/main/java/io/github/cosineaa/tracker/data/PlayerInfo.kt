package io.github.cosineaa.tracker.data

data class PlayerInfo(
    val playerId: String,
    val playerSlug: String,
    val playerImage: String,

    val playerName: String,
    val jerseyNumber: String,
    val position: String,

    val height: String,
    val weight: String,

    val points: String,
    val assist: String,
    val rebound: String,

    val draftYear: String,
    val draftRound: String,
    val draftNumber: String,

    val country: String,
    val college: String,

    val teamId: String,
    val teamKoreanName: String,
    val teamEnglishName: String,
    val teamEnglishShortName: String
) : java.io.Serializable {

}