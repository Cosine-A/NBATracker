package io.github.cosineaa.tracker.util

fun String.toKilogram(): String = (String.format("%.1f", this.toDouble() * 0.453592).toDouble()).toString()
fun String.toCentimeter(): String {
    val allFeat = this.split("-")
    val feat = ((allFeat[0].toDouble() * 12.0) + allFeat[1].toDouble()) * 2.54
    return String.format("%.1f", feat)
}
fun String.getEnglishTeamName(): String {
    if (this == "올랜도 매직") return "Orlando Magic"
    if (this == "휴스턴 로키츠") return "Houston Rockets"
    if (this == "LA 레이커스") return "Los Angeles Lakers"
    if (this == "샬럿 호네츠") return "Charlotte Hornets"
    if (this == "디트로이트 피스톤즈") return "Detroit Pistons"
    if (this == "새크라멘토 킹스") return "Sacramento Kings"
    if (this == "브루클린 네츠") return "Brooklyn Nets"
    if (this == "마이애미 히트") return "Miami Heat"
    if (this == "골든스테이트 워리어스") return "Golden State Warriors"
    if (this == "오클라호마시티 썬더") return "Oklahoma City Thunder"
    if (this == "워싱턴 위저즈") return "Washington Wizards"
    if (this == "필라델피아 세븐티식서스") return "Philadelphia 76ers"
    if (this == "샌안토니오 스퍼스") return "San Antonio Spurs"
    if (this == "미네소타 팀버울브스") return "Minnesota Timberwolves"
    if (this == "시카고 불스") return "Chicago Bulls"
    if (this == "뉴욕 닉스") return "New York Knicks"
    if (this == "인디애나 페이서스") return "Indiana Pacers"
    if (this == "뉴올리언스 펠리컨스") return "New Orleans Pelicans"
    if (this == "토론토 랩터스") return "Toronto Raptors"
    if (this == "LA 클리퍼스") return "Los Angeles Clippers"
    if (this == "멤피스 그리즐리스") return "Memphis Grizzlies"
    if (this == "댈러스 매버릭스") return "Dallas Mavericks"
    if (this == "보스턴 셀틱스") return "Boston Celtics"
    if (this == "애틀랜타 호크스") return "Atlanta Hawks"
    if (this == "피닉스 선즈") return "Phoenix Suns"
    if (this == "포틀랜드 트레일블레이저스") return "Portland Trail Blazers"
    if (this == "덴버 너기츠") return "Denver Nuggets"
    if (this == "유타 재즈") return "Utah Jazz"
    if (this == "클리블랜드 캐벌리어스") return "Cleveland Cavaliers"
    if (this == "밀워키 벅스") return "Milwaukee Bucks"
    return "None"
}
fun String.getKoreanTeamName(): String {
    if (this == "Orlando Magic") return "올랜도 매직"
    if (this == "Houston Rockets") return "휴스턴 로키츠"
    if (this == "Los Angeles Lakers") return "LA 레이커스"
    if (this == "Charlotte Hornets") return "샬럿 호네츠"
    if (this == "Detroit Pistons") return "디트로이트 피스톤즈"
    if (this == "Sacramento Kings") return "새크라멘토 킹스"
    if (this == "Brooklyn Nets") return "브루클린 네츠"
    if (this == "Miami Heat") return "마이애미 히트"
    if (this == "Golden State Warriors") return "골든스테이트 워리어스"
    if (this == "Oklahoma City Thunder") return "오클라호마시티 썬더"
    if (this == "Washington Wizards") return "워싱턴 위저즈"
    if (this == "Philadelphia 76ers") return "필라델피아 세븐티식서스"
    if (this == "San Antonio Spurs") return "샌안토니오 스퍼스"
    if (this == "Minnesota Timberwolves") return "미네소타 팀버울브스"
    if (this == "Chicago Bulls") return "시카고 불스"
    if (this == "New York Knicks") return "뉴욕 닉스"
    if (this == "Indiana Pacers") return "인디애나 페이서스"
    if (this == "New Orleans Pelicans") return "뉴올리언스 펠리컨스"
    if (this == "Toronto Raptors") return "토론토 랩터스"
    if (this == "Los Angeles Clippers") return "LA 클리퍼스"
    if (this == "Memphis Grizzlies") return "멤피스 그리즐리스"
    if (this == "Dallas Mavericks") return "댈러스 매버릭스"
    if (this == "Boston Celtics") return "보스턴 셀틱스"
    if (this == "Atlanta Hawks") return "애틀랜타 호크스"
    if (this == "Phoenix Suns") return "피닉스 선즈"
    if (this == "Portland Trail Blazers") return "포틀랜드 트레일블레이저스"
    if (this == "Denver Nuggets") return "덴버 너기츠"
    if (this == "Utah Jazz") return "유타 재즈"
    if (this == "Cleveland Cavaliers") return "클리블랜드 캐벌리어스"
    if (this == "Milwaukee Bucks") return "밀워키 벅스"
    return "None"
}
fun String.getEnglishShortTeamName(): String {
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