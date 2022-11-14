package io.github.cosineaa

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import io.github.cosineaa.MainActivity.Companion.esamanru
import io.github.cosineaa.MainActivity.Companion.playerJson
import io.github.cosineaa.MainActivity.Companion.tracker
import io.github.cosineaa.tracker.data.PlayerInfo
import io.github.cosineaa.tracker.data.TeamInfo
import io.github.cosineaa.util.*

private lateinit var allInfo: ArrayList<TeamInfo>
private lateinit var teamInfo: TeamInfo

class ConferenceActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        allInfo = intent.getSerializableExtra("AllTeam") as ArrayList<TeamInfo>
        teamInfo = intent.getSerializableExtra("TeamInfo") as TeamInfo

        println("순위 " + allInfo.getPointsPerGameRank(teamInfo.teamKoreanShortName))
        println("값 " + teamInfo.ppg)

        setContent {
            TeamView()
        }
    }
}

@Composable
fun TeamView() {
    Column {
        TeamStatCard()
        TeamPlayerCard()
    }
}
@Composable
fun TeamStatCard() {
    Card(
        shape = RoundedCornerShape(15.size()),
        modifier = Modifier.padding(5.size()),
        elevation = 10.size()
    ) {
        Column(verticalArrangement = Arrangement.SpaceBetween) {
            TeamImage()
            TeamStat()
        }
    }
}
@Composable
fun TeamImage() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier.fillMaxWidth()
    ) {
        AsyncImage(modifier = Modifier.size(120.size()),//.background(Color.Blue),
            model = teamInfo.teamImage, contentDescription = teamInfo.teamKoreanShortName)
        Column(
            //modifier = Modifier.background(Color.Yellow),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = teamInfo.teamKoreanName, fontFamily = esamanru, fontWeight = FontWeight.Bold, fontSize = 23.sp)
            Text(text = teamInfo.teamEnglishName, fontFamily = esamanru, fontWeight = FontWeight.Medium, fontSize = 18.sp)
            Text(text = "", fontFamily = esamanru, fontWeight = FontWeight.Medium, fontSize = 10.sp)
            Text(text = "${teamInfo.gameWin}승 ${teamInfo.gameLose}패", fontFamily = esamanru, fontWeight = FontWeight.Medium, fontSize = 15.sp)
            Text(text = "${teamInfo.conference}컨퍼런스 ${teamInfo.conferenceRank}등", fontFamily = esamanru, fontWeight = FontWeight.Medium, fontSize = 15.sp)
        }
    }
}
@Composable
fun TeamStat() {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            val ppgStat = teamInfo.ppg.getStat()
            val ppgRank = allInfo.getPointsPerGameRank(teamInfo.teamKoreanShortName)
            TeamStatInfo(width = 0.5f, statName = "득점", rank = ppgRank, stat = ppgStat)

            val oppgStat = teamInfo.oppg.getStat()
            val oppgRank = allInfo.getOpponentPointsPerGameRank(teamInfo.teamKoreanShortName)
            TeamStatInfo(width = 1f, statName = "실점", rank = oppgRank, stat = oppgStat)
        }
        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            val reboundStat = teamInfo.rpg.getStat()
            val reboundRank = allInfo.getReboundsPerGameRank(teamInfo.teamKoreanShortName)
            TeamStatInfo(width = 0.5f, statName = "리바운드", rank = reboundRank, stat = reboundStat)

            val assistStat = teamInfo.apg.getStat()
            val assistRank = allInfo.getAssistsPerGameRank(teamInfo.teamKoreanShortName)
            TeamStatInfo(width = 1f, statName = "어시스트", rank = assistRank, stat = assistStat)
        }
    }
}
@Composable
fun TeamStatInfo(width: Float, statName: String, rank: String, stat: String) {
    Card(
        modifier = Modifier
            .padding(5.size())
            .fillMaxWidth(width),
        shape = RoundedCornerShape(10.size()),
        elevation = 5.size()
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = statName, textAlign = TextAlign.Center,
                fontFamily = esamanru, fontWeight = FontWeight.Medium, fontSize = 16.sp
            )
            Text(
                text = "${rank}등", textAlign = TextAlign.Center,
                fontFamily = esamanru, fontWeight = FontWeight.Medium, fontSize = 23.sp
            )
            Text(
                text = stat, textAlign = TextAlign.Center,
                fontFamily = esamanru, fontWeight = FontWeight.Medium, fontSize = 16.sp
            )
        }
    }
}
@Composable
fun TeamPlayerCard() {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            //.background(color = Color.Red)
    ) {
        val teamPlayers = tracker.getPlayerFromTeam(playerJson, teamInfo.teamEnglishShortName)
        itemsIndexed(teamPlayers) { _, item -> TeamPlayer(item) }
    }
}
@Composable
fun TeamPlayer(info: PlayerInfo) {
    Card(
        modifier = Modifier
            .padding(2.size())
            .fillMaxWidth()
            .height(50.size()),
        backgroundColor = Color.White,
        contentColor = Color.Black,
        elevation = 5.dp)
    {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Text(modifier = Modifier.fillMaxWidth(0.36f),
                text = info.playerName, fontFamily = esamanru, fontWeight = FontWeight.Light)

            Text(modifier = Modifier.fillMaxWidth(0.2f),
                text = info.jerseyNumber, fontFamily = esamanru, fontWeight = FontWeight.Light)

            Text(modifier = Modifier.fillMaxWidth(0.4f),
                text = info.position, fontFamily = esamanru, fontWeight = FontWeight.Light)

            Text(modifier = Modifier.fillMaxWidth(0.5f),
                text = info.height, fontFamily = esamanru, fontWeight = FontWeight.Light)

            Text(modifier = Modifier.fillMaxWidth(0.6f),
                text = info.weight, fontFamily = esamanru, fontWeight = FontWeight.Light)
        }
    }
}