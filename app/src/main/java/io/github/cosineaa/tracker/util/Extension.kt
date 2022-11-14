package io.github.cosineaa.tracker.util

import kotlin.math.round

fun String.toKilogram(): String = (String.format("%.1f", this.toDouble() * 0.453592).toDouble()).toString()
fun String.toCentimeter(): String {
    val allFeat = this.split("-")
    return (round(((allFeat[0].toDouble() * 12.0) + allFeat[1].toDouble()) * 2.54)).toString()
}
fun String.replaceTeamToEnglish(): String {
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
fun String.getShortTeam(): String {
    if (this == "올랜도 매직") return "ORL"
    if (this == "휴스턴 로키츠") return "HOU"
    if (this == "LA 레이커스") return "LAL"
    if (this == "샬럿 호네츠") return "CHA"
    if (this == "디트로이트 피스톤즈") return "DET"
    if (this == "새크라멘토 킹스") return "SAC"
    if (this == "브루클린 네츠") return "BKN"
    if (this == "마이애미 히트") return "MIA"
    if (this == "골든스테이트 워리어스") return "GSW"
    if (this == "오클라호마시티 썬더") return "OKC"
    if (this == "워싱턴 위저즈") return "WAS"
    if (this == "필라델피아 세븐티식서스") return "PHI"
    if (this == "샌안토니오 스퍼스") return "SAS"
    if (this == "미네소타 팀버울브스") return "MIN"
    if (this == "시카고 불스") return "CHI"
    if (this == "뉴욕 닉스") return "NYK"
    if (this == "인디애나 페이서스") return "IND"
    if (this == "뉴올리언스 펠리컨스") return "NOP"
    if (this == "토론토 랩터스") return "TOR"
    if (this == "LA 클리퍼스") return "LAC"
    if (this == "멤피스 그리즐리스") return "MEM"
    if (this == "댈러스 매버릭스") return "DAL"
    if (this == "보스턴 셀틱스") return "BOS"
    if (this == "애틀랜타 호크스") return "ATL"
    if (this == "피닉스 선즈") return "PHX"
    if (this == "포틀랜드 트레일블레이저스") return "POR"
    if (this == "덴버 너기츠") return "DEN"
    if (this == "유타 재즈") return "UTA"
    if (this == "클리블랜드 캐벌리어스") return "CLE"
    if (this == "밀워키 벅스") return "MIL"
    return "None"
}
fun String.replaceConference(): String {
    return this.replace("컨퍼런스", "")
}

//@Composable
//fun LoadConferenceTeamImage(teamShortName: String) {
//    val modifier = Modifier.size(40.dp, 40.dp)
//    if (teamShortName == "MIL")
//        Image(painter = painterResource(id = R.drawable.mil), modifier = modifier, contentDescription = teamShortName)
//    if (teamShortName == "UTA")
//        Image(painter = painterResource(id = R.drawable.uta), modifier = modifier, contentDescription = teamShortName)
//    if (teamShortName == "POR")
//        Image(painter = painterResource(id = R.drawable.por), modifier = modifier, contentDescription = teamShortName)
//    if (teamShortName == "BOS")
//        Image(painter = painterResource(id = R.drawable.bos), modifier = modifier, contentDescription = teamShortName)
//    if (teamShortName == "MEM")
//        Image(painter = painterResource(id = R.drawable.mem), modifier = modifier, contentDescription = teamShortName)
//    if (teamShortName == "DEN")
//        Image(painter = painterResource(id = R.drawable.den), modifier = modifier, contentDescription = teamShortName)
//    if (teamShortName == "PHX")
//        Image(painter = painterResource(id = R.drawable.phx), modifier = modifier, contentDescription = teamShortName)
//    if (teamShortName == "CLE")
//        Image(painter = painterResource(id = R.drawable.cle), modifier = modifier, contentDescription = teamShortName)
//    if (teamShortName == "ATL")
//        Image(painter = painterResource(id = R.drawable.atl), modifier = modifier, contentDescription = teamShortName)
//    if (teamShortName == "LAC")
//        Image(painter = painterResource(id = R.drawable.lac), modifier = modifier, contentDescription = teamShortName)
//    if (teamShortName == "DAL")
//        Image(painter = painterResource(id = R.drawable.dal), modifier = modifier, contentDescription = teamShortName)
//    if (teamShortName == "TOR")
//        Image(painter = painterResource(id = R.drawable.tor), modifier = modifier, contentDescription = teamShortName)
//    if (teamShortName == "NOP")
//        Image(painter = painterResource(id = R.drawable.nop), modifier = modifier, contentDescription = teamShortName)
//    if (teamShortName == "NYK")
//        Image(painter = painterResource(id = R.drawable.nyk), modifier = modifier, contentDescription = teamShortName)
//    if (teamShortName == "WAS")
//        Image(painter = painterResource(id = R.drawable.was), modifier = modifier, contentDescription = teamShortName)
//    if (teamShortName == "SAS")
//        Image(painter = painterResource(id = R.drawable.sas), modifier = modifier, contentDescription = teamShortName)
//    if (teamShortName == "CHI")
//        Image(painter = painterResource(id = R.drawable.chi), modifier = modifier, contentDescription = teamShortName)
//    if (teamShortName == "SAC")
//        Image(painter = painterResource(id = R.drawable.sac), modifier = modifier, contentDescription = teamShortName)
//    if (teamShortName == "IND")
//        Image(painter = painterResource(id = R.drawable.ind), modifier = modifier, contentDescription = teamShortName)
//    if (teamShortName == "OKC")
//        Image(painter = painterResource(id = R.drawable.okc), modifier = modifier, contentDescription = teamShortName)
//    if (teamShortName == "GSW")
//        Image(painter = painterResource(id = R.drawable.gsw), modifier = modifier, contentDescription = teamShortName)
//    if (teamShortName == "PHI")
//        Image(painter = painterResource(id = R.drawable.phi), modifier = modifier, contentDescription = teamShortName)
//    if (teamShortName == "MIA")
//        Image(painter = painterResource(id = R.drawable.mia), modifier = modifier, contentDescription = teamShortName)
//    if (teamShortName == "BKN")
//        Image(painter = painterResource(id = R.drawable.bkn), modifier = modifier, contentDescription = teamShortName)
//    if (teamShortName == "MIN")
//        Image(painter = painterResource(id = R.drawable.min), modifier = modifier, contentDescription = teamShortName)
//    if (teamShortName == "ORL")
//        Image(painter = painterResource(id = R.drawable.orl), modifier = modifier, contentDescription = teamShortName)
//    if (teamShortName == "DET")
//        Image(painter = painterResource(id = R.drawable.det), modifier = modifier, contentDescription = teamShortName)
//    if (teamShortName == "CHA")
//        Image(painter = painterResource(id = R.drawable.cha), modifier = modifier, contentDescription = teamShortName)
//    if (teamShortName == "HOU")
//        Image(painter = painterResource(id = R.drawable.hou), modifier = modifier, contentDescription = teamShortName)
//    if (teamShortName == "LAL")
//        Image(painter = painterResource(id = R.drawable.lal), modifier = modifier, contentDescription = teamShortName)
//}