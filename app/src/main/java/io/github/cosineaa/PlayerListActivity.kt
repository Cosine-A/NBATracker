package io.github.cosineaa

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.github.cosineaa.MainActivity.Companion.eastTeamJson
import io.github.cosineaa.MainActivity.Companion.playerJson
import io.github.cosineaa.MainActivity.Companion.tracker
import io.github.cosineaa.MainActivity.Companion.westTeamJson
import io.github.cosineaa.PlayerListActivity.Companion.playerListInstance
import io.github.cosineaa.tracker.data.PlayerInfo
import io.github.cosineaa.util.size

private lateinit var nowPage: List<PlayerInfo>
private var page = 1

class PlayerListActivity : ComponentActivity() {

    companion object {
        lateinit var playerListInstance: PlayerListActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        playerListInstance = this
        page = 1

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
        Box(contentAlignment = Alignment.BottomCenter) {
            ScrollPlayerList()
            PageController()
        }
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
        Text(modifier = Modifier
            .fillMaxWidth(0.57f)
            .padding(start = 10.size()),
            text = "선수", fontFamily = MainActivity.esamanru, fontWeight = FontWeight.Medium)

        Text(modifier = Modifier.fillMaxWidth(0.4f),
            text = "등번호", fontFamily = MainActivity.esamanru, fontWeight = FontWeight.Medium)

        Text(modifier = Modifier.fillMaxWidth(1f),
            text = "포지션", fontFamily = MainActivity.esamanru, fontWeight = FontWeight.Medium)
    }
}

@Composable
fun ScrollPlayerList() {
    nowPage = tracker.getPlayerFromPage(playerJson, 1)
    LazyColumn {
        itemsIndexed(nowPage) { _, item -> PlayerList(item) }
        item { Card(modifier = Modifier
            .fillMaxWidth()
            .height(50.size())) {} }
    }
}

@Composable
fun PageController() {
    Row(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .fillMaxHeight(0.07f),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        val size = 35.size()
        Image(modifier = Modifier.size(size),
            painter = painterResource(id = R.drawable.ic_baseline_first_page_24), contentDescription = "처음")
        Image(modifier = Modifier.size(size),
            painter = painterResource(id = R.drawable.ic_baseline_navigate_before_24), contentDescription = "이전")
        Image(modifier = Modifier.size(size),
            painter = painterResource(id = R.drawable.ic_baseline_navigate_next_24), contentDescription = "다음")
        Image(modifier = Modifier.size(size),
            painter = painterResource(id = R.drawable.ic_baseline_last_page_24), contentDescription = "마지막")
    }
}

@Composable
fun PlayerList(info: PlayerInfo) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.size())
            .padding(5.size())
            .clickable {
                val intent = Intent(playerListInstance, PlayerActivity::class.java)
                intent.putExtra("PlayerInfo", info)
                intent.putExtra(
                    "TeamImage",
                    tracker.getImageFromTeam(eastTeamJson, westTeamJson, info.teamEnglishName)
                )
                playerListInstance.startActivity(intent)
            },
        backgroundColor = Color.White,
        contentColor = Color.Black,
        elevation = 2.dp)
    {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Text(modifier = Modifier
                .fillMaxWidth(0.6f)
                .padding(start = 10.size()),
                text = info.playerName, fontFamily = MainActivity.esamanru, fontWeight = FontWeight.Medium)

            Text(modifier = Modifier.fillMaxWidth(0.4f),
                text = info.jerseyNumber, fontFamily = MainActivity.esamanru, fontWeight = FontWeight.Light)

            Text(modifier = Modifier.fillMaxWidth(1f),
                text = info.position, fontFamily = MainActivity.esamanru, fontWeight = FontWeight.Light)
        }
    }
}
