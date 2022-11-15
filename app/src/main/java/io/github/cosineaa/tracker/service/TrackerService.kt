package io.github.cosineaa.tracker.service

import io.github.cosineaa.tracker.data.PlayerInfo
import io.github.cosineaa.tracker.data.TeamInfo
import kotlinx.coroutines.CoroutineScope
import org.json.simple.JSONArray
import java.util.concurrent.ExecutorService

interface TrackerService {

    val executor: ExecutorService

    val playerUrl: String

    val conferenceUrl: String

    fun getPlayerJson(): JSONArray

    fun getPlayerFromPage(json: JSONArray, page: Int): List<PlayerInfo> // 50줄 기준, 총 501줄

    fun getPlayerFromName(json: JSONArray, searchName: String): List<PlayerInfo>

    fun getPlayerFromTeam(json: JSONArray, searchTeam: String): List<PlayerInfo>

    fun getPlayerFromPosition(json: JSONArray, searchPosition: String): List<PlayerInfo>

    fun getConferenceJson(conference: String): JSONArray

    fun getConferenceRank(json: JSONArray, reversed: Boolean): List<TeamInfo>

    fun getAllConferenceRank(conference1: JSONArray, conference2: JSONArray, reversed: Boolean): List<TeamInfo>

    fun getImageFromTeam(conference1: JSONArray, conference2: JSONArray, searchTeam: String): String
}