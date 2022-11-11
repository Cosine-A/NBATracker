package io.github.cosineaa.tracker.util

import kotlin.math.round

fun String.toKilogram(): String = (String.format("%.1f", this.toDouble() * 0.453592).toDouble()).toString()
fun String.toCentimeter(): String {
    val allFeat = this.split("-")
    return (round(((allFeat[0].toDouble() * 12.0) + allFeat[1].toDouble()) * 2.54)).toString()
}
fun String.replaceTeam(): String {
    return this
        .replace("올랜도 매직", "Orlando Magic")
        .replace("휴스턴 로키츠", "Houston Rockets")
        .replace("LA 레이커스", "Los Angeles Lakers")
        .replace("샬럿 호네츠", "Charlotte Hornets")
        .replace("디트로이트 피스톤즈", "Detroit Pistons")
        .replace("새크라멘토 킹스", "Sacramento Kings")
        .replace("브루클린 네츠", "Brooklyn Nets")
        .replace("마이애미 히트", "Miami Heat")
        .replace("골든스테이트 워리어스", "Golden State Warriors")
        .replace("오클라호마시티 썬더", "Oklahoma City Thunder")
        .replace("워싱턴 위저즈", "Washington Wizards")
        .replace("필라델피아 세븐티식서스", "Philadelphia 76ers")
        .replace("샌안토니오 스퍼스", "San Antonio Spurs")
        .replace("미네소타 팀버울브스", "Minnesota Timberwolves")
        .replace("시카고 불스", "Chicago Bulls")
        .replace("뉴욕 닉스", "New York Knicks")
        .replace("인디애나 페이서스", "Indiana Pacers")
        .replace("뉴올리언스 펠리컨스", "New Orleans Pelicans")
        .replace("토론토 랩터스", "Toronto Raptors")
        .replace("LA 클리퍼스", "Los Angeles Clippers")
        .replace("멤피스 그리즐리스", "Memphis Grizzlies")
        .replace("댈러스 매버릭스", "Dallas Mavericks")
        .replace("보스턴 셀틱스", "Boston Celtics")
        .replace("애틀랜타 호크스", "Atlanta Hawks")
        .replace("피닉스 선즈", "Phoenix Suns")
        .replace("포틀랜드 트레일블레이저스", "Portland Trail Blazers")
        .replace("덴버 너기츠", "Denver Nuggets")
        .replace("유타 재즈", "Utah Jazz")
        .replace("클리블랜드 캐벌리어스", "Cleveland Cavaliers")
        .replace("밀워키 벅스", "Milwaukee Bucks")
}
fun String.replaceConference(): String {
    return this.replace("컨퍼런스", "")
}