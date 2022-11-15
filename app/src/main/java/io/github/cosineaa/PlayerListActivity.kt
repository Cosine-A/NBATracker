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
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import io.github.cosineaa.MainActivity.Companion.eastTeamJson
import io.github.cosineaa.MainActivity.Companion.playerJson
import io.github.cosineaa.MainActivity.Companion.tracker
import io.github.cosineaa.MainActivity.Companion.westTeamJson
import io.github.cosineaa.PlayerListActivity.Companion.playerListInstance
import io.github.cosineaa.tracker.data.PlayerInfo
import io.github.cosineaa.util.size

private lateinit var nowPage: List<PlayerInfo>

class PlayerListActivity : ComponentActivity() {

    companion object {
        lateinit var playerListInstance: PlayerListActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        playerListInstance = this

        setContent {
            PlayerListView()
        }
    }
}

@Composable
fun PlayerListTopAppBar() {
    TopAppBar(
        title = { Text(text = "선수", color = Color.White, fontFamily = MainActivity.esamanru, fontWeight = FontWeight.Bold) },
        backgroundColor = Color.Black
    )
}

@Composable
fun PlayerListView() {
    Column {
        PlayerListTopAppBar()
        PlayerListStatTypeGuide()
        ScrollPlayerList()
    }
}

@Composable
fun PlayerListStatTypeGuide() {
    Row(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .height(30.size()),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly)
    {
        Text(
            modifier = Modifier.fillMaxWidth(0.07f),
            text = "순위", fontFamily = MainActivity.esamanru, fontWeight = FontWeight.Medium)
        Text(modifier = Modifier.fillMaxWidth(0.05f),
            text = "   ", fontFamily = MainActivity.esamanru, fontWeight = FontWeight.Medium)
        Text(modifier = Modifier.fillMaxWidth(0.25f),
            text = "팀", fontFamily = MainActivity.esamanru, fontWeight = FontWeight.Medium)
        Text(modifier = Modifier.fillMaxWidth(0.11f),
            text = "경기", fontFamily = MainActivity.esamanru, fontWeight = FontWeight.Medium)
        Text(modifier = Modifier.fillMaxWidth(0.07f),
            text = "승", fontFamily = MainActivity.esamanru, fontWeight = FontWeight.Medium)
        Text(modifier = Modifier.fillMaxWidth(0.08f),
            text = "패", fontFamily = MainActivity.esamanru, fontWeight = FontWeight.Medium)
        Text(modifier = Modifier.fillMaxWidth(0.18f),
            text = "승률", fontFamily = MainActivity.esamanru, fontWeight = FontWeight.Medium)
        Text(modifier = Modifier.fillMaxWidth(0.2f),
            text = "연속", fontFamily = MainActivity.esamanru, fontWeight = FontWeight.Medium)
    }
}

@Composable
fun ScrollPlayerList() {
    nowPage = tracker.getPlayerFromPage(playerJson, 1)
    LazyColumn {
        itemsIndexed(nowPage) { _, item -> PlayerList(item) }
    }
}

@Composable
fun PlayerList(info: PlayerInfo) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.size())
            .padding(2.size())
            .clickable {
                val intent = Intent(playerListInstance, PlayerActivity::class.java)
                intent.putExtra("PlayerInfo", info)
                intent.putExtra("TeamImage", tracker.getImageFromTeam(eastTeamJson, westTeamJson, info.teamEnglishName))
                playerListInstance.startActivity(intent)
            },
        backgroundColor = Color.White,
        contentColor = Color.Black)
    {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Text(modifier = Modifier
                .fillMaxWidth(0.5f)
                .padding(start = 10.size()),
                text = info.playerName, fontFamily = MainActivity.esamanru, fontWeight = FontWeight.Light)

            Text(modifier = Modifier.fillMaxWidth(0.3f),
                text = info.jerseyNumber, fontFamily = MainActivity.esamanru, fontWeight = FontWeight.Light)

            Text(modifier = Modifier.fillMaxWidth(0.3f),
                text = info.position, fontFamily = MainActivity.esamanru, fontWeight = FontWeight.Light)
        }
    }
}
