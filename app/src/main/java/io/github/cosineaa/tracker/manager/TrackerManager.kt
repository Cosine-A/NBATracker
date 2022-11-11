package io.github.cosineaa.tracker.manager

import io.github.cosineaa.tracker.data.PlayerInfo
import io.github.cosineaa.tracker.data.TeamInfo
import io.github.cosineaa.tracker.service.TrackerService
import io.github.cosineaa.tracker.util.replaceConference
import io.github.cosineaa.tracker.util.replaceTeam
import io.github.cosineaa.tracker.util.toCentimeter
import io.github.cosineaa.tracker.util.toKilogram
import kotlinx.coroutines.*
import kotlinx.coroutines.Runnable
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import org.jsoup.Jsoup
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.nio.charset.StandardCharsets
import java.util.*
import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class TrackerManager : TrackerService {

    override val executor: ExecutorService = Executors.newCachedThreadPool()
    override val playerUrl: String = "https://www.nba.com/players"
    override val conferenceUrl: String = "https://sports.daum.net/prx/hermes/api/team/rank.json?leagueCode=nba&seasonKey=20222023&page=1&pageSize=100&subLeague="

    override fun getPlayerJson(): JSONArray {
        val runnable = Callable {
            val jsoup = Jsoup.connect(playerUrl).post()
            val document = jsoup.getElementById("__NEXT_DATA__")?.firstChild().toString()
            val jsonParser = JSONParser()
            val json = jsonParser.parse(document) as JSONObject
            val props = json["props"] as JSONObject
            val pageProps = props["pageProps"] as JSONObject
            pageProps["players"] as JSONArray
        }
        val future = executor.submit(runnable)
        return future.get()
    }

    override fun getPlayerFromPage(json: JSONArray, page: Int): List<PlayerInfo> {
        val playerList = mutableListOf<PlayerInfo>()
        for (loop in 0 + ((page - 1) * 50) until page * 50) {
            if (loop == json.size) break
            val playerInfo = json[loop] as JSONObject
            playerList.add(getPlayerData(playerInfo))
        }
        return playerList
    }

    override fun getPlayerFromName(json: JSONArray, searchName: String): List<PlayerInfo> {
        val playerList = mutableListOf<PlayerInfo>()
        for (loop in 0 until json.size) {
            val playerInfo = json[loop] as JSONObject
            val playerName = "${playerInfo["PLAYER_SLUG"]}"
            if (playerName.contains(searchName.lowercase(Locale.getDefault()))) {
                playerList.add(getPlayerData(playerInfo))
            }
        }
        return playerList
    }

    override fun getPlayerFromTeam(json: JSONArray, searchTeam: String): List<PlayerInfo> {
        val playerList = mutableListOf<PlayerInfo>()
        for (loop in 0 until json.size) {
            val playerInfo = json[loop] as JSONObject
            val playerTeamId = playerInfo["TEAM_ID"].toString()
            if (playerTeamId == searchTeam) {
                playerList.add(getPlayerData(playerInfo))
            }
        }
        return playerList
    }

    override fun getPlayerFromPosition(json: JSONArray, searchPosition: String): List<PlayerInfo> {
        val playerList = mutableListOf<PlayerInfo>()
        for (loop in 0 until json.size) {
            val playerInfo = json[loop] as JSONObject
            val playerPosition = playerInfo["POSITION"].toString()
            if (playerPosition.contains(searchPosition)) {
                val info = getPlayerData(playerInfo)
                playerList.add(info)
            }
        }
        return playerList
    }

    private fun getPlayerData(info: JSONObject): PlayerInfo {
        val playerId = info["PERSON_ID"].toString()
        val playerSlug = info["PLAYER_SLUG"].toString()
        val playerImage = "https://cdn.nba.com/headshots/nba/latest/1040x760/$playerId.png"

        val playerName = "${info["PLAYER_FIRST_NAME"]} ${info["PLAYER_LAST_NAME"]}"
        val jerseyNumber = info["JERSEY_NUMBER"] ?: "None"
        val position = getPosition(info["POSITION"].toString())

        val height = info["HEIGHT"] ?: "None"
        val weight = info["WEIGHT"] ?: "None"

        val points = info["PTS"] ?: "None"
        val assist = info["AST"] ?: "None"
        val rebound = info["REB"] ?: "None"

        val draftYear = info["DRAFT_YEAR"] ?: "언드래프티"
        val draftRound = info["DRAFT_ROUND"] ?: "언드래프티"
        val draftNumber = info["DRAFT_NUMBER"] ?: "언드래프티"

        val country = info["COUNTRY"] ?: "None"
        val college = info["COLLEGE"] ?: "None"

        val teamId = info["TEAM_ID"] ?: "None"
        val teamName = "${info["TEAM_CITY"] ?: "None"} ${info["TEAM_NAME"] ?: "None"}"
        val abbreviation = info["TEAM_ABBREVIATION"] ?: "None"

        return PlayerInfo(
            playerId,
            playerSlug,
            playerImage,
            playerName,
            jerseyNumber.toString(),
            position,
            height.toString().toCentimeter(),
            weight.toString().toKilogram(),
            points.toString(),
            assist.toString(),
            rebound.toString(),
            draftYear.toString(),
            draftRound.toString(),
            draftNumber.toString(),
            country.toString(),
            college.toString(),
            teamId.toString(),
            teamName,
            abbreviation.toString()
        )
    }
    private fun getPosition(position: String): String {
        return if (position.contains("-")) {
            val positions = position.split("-")
            "${getPositionFullName(positions[0])}/${getPositionFullName(positions[1])}"
        } else {
            getPositionFullName(position)
        }
    }
    private fun getPositionFullName(position: String): String {
        return when (position) {
            "G" -> "가드"
            "F" -> "포워드"
            "C" -> "센터"
            else -> "None"
        }
    }

    override fun getConferenceJson(conference: String): JSONArray {
        val value = getConferenceFromUrl("$conferenceUrl$conference")
        val parser = JSONParser()
        val json = parser.parse(value) as JSONObject
        return json["list"] as JSONArray
    }

    override fun getConferenceRank(json: JSONArray, reversed: Boolean): List<TeamInfo> {
        val teamList = mutableListOf<TeamInfo>()
        for (loop in 0 until json.size) {
            val teamInfo = json[loop] as JSONObject
            teamList.add(getTeamData(teamInfo))
        }
        teamList.sort()
        if (reversed) teamList.reverse()
        return teamList
    }

    override fun getAllConferenceRank(conference1: JSONArray, conference2: JSONArray, reversed: Boolean): List<TeamInfo> {
        val rank = mutableListOf<TeamInfo>().apply {
            addAll(getConferenceRank(conference1, false))
            addAll(getConferenceRank(conference2, false))
        }
        val rankList = rank.sortedBy { it.gameRate }.reversed()
        return if (reversed) rankList.reversed() else rankList
    }

    private fun getTeamData(info: JSONObject): TeamInfo {
        val teamName = info["nameMain"] ?: "None"
        val teamImage = info["imageUrl"] ?: "None"

        val rank = info["rank"] as JSONObject
        val rate = rank["wpct"] ?: "None"
        val gameRate = "${rate}000".substring(0..4)
        val gameAll = rank["game"] ?: "None"
        val gameWin = rank["win"] ?: "None"
        val gameLose = rank["loss"] ?: "None"
        val gameContinuity = rank["streak"] ?: "None"

        val homeWin = rank["homeWin"] ?: "None"
        val homeLose = rank["homeLoss"] ?: "None"
        val awayWin = rank["awayWin"] ?: "None"
        val awayLose = rank["awayLoss"] ?: "None"

        val league = info["subLeague1depth"] as JSONObject
        val conference = league["name"] ?: "None"
        val conferenceRank = rank["conferenceRank"] ?: "None"

        return TeamInfo(
            teamName.toString().replaceTeam(),
            teamImage.toString(),
            gameRate,
            gameAll.toString(),
            gameWin.toString(),
            gameLose.toString(),
            gameContinuity.toString(),
            homeWin.toString(),
            homeLose.toString(),
            awayWin.toString(),
            awayLose.toString(),
            conference.toString().replaceConference(),
            conferenceRank.toString()
        )
    }
    private fun getConferenceFromUrl(url: String): String {
        val runnable = Callable {
            var json: java.lang.StringBuilder
            URL(url).openStream().use { input ->
                val isr = InputStreamReader(input, StandardCharsets.UTF_8)
                val reader = BufferedReader(isr)
                json = StringBuilder()
                var c: Int
                while (reader.read().also { c = it } != -1) {
                    json.append(c.toChar())
                }
            }
            json.toString()
        }
        val future = executor.submit(runnable)
        return future.get()
    }
}