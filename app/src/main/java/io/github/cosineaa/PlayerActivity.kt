package io.github.cosineaa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import io.github.cosineaa.tracker.data.PlayerInfo
import io.github.cosineaa.util.size

private lateinit var playerInfo: PlayerInfo
private lateinit var teamImage: String
private var color = Color.White

class PlayerActivity  : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        playerInfo = intent.getSerializableExtra("PlayerInfo") as PlayerInfo
        teamImage = intent.getStringExtra("TeamImage") as String

        setContent {
            PlayerView()
        }
    }
}

@Composable
fun PlayerView() {
    Column {
        PlayerCard()
        PlayerStat()
    }
}
@Composable
fun PlayerCard() {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(7.size())
        .background(color = Color.White),
        shape = RoundedCornerShape(20.size()),
        elevation = 5.size()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PlayerImage()
            PlayerNameAndTeam()
        }
    }
}
@Composable
fun PlayerImage() {
    Box(contentAlignment = Alignment.Center) {
        if (teamImage == "None") {
            Image(painter = painterResource(id = R.drawable.error), contentDescription = "Error")
        } else {
            AsyncImage(modifier = Modifier
                .size(220.size())
                .alpha(0.4f),
                model = teamImage, contentDescription = playerInfo.teamEnglishShortName)
        }

        AsyncImage(modifier = Modifier.size(200.size()),
            model = playerInfo.playerImage, contentDescription = playerInfo.playerSlug)
    }
}
@Composable
fun PlayerNameAndTeam() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = playerInfo.playerName, color = Color.Black,
            fontFamily = MainActivity.esamanru, fontWeight = FontWeight.Bold, fontSize = 25.sp)

        Text(text = playerInfo.teamKoreanName, color = Color.Black,
            fontFamily = MainActivity.esamanru, fontWeight = FontWeight.Medium, fontSize = 19.sp)

        Text(text = "")
    }
}
@Composable
fun PlayerStat() {
    val padding = 7.size()
    Card(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .padding(top = padding / 2, start = padding, end = padding)
        .background(color = Color.White),
        shape = RoundedCornerShape(20.size()),
        elevation = 5.size()
    ) {
        val scroll = rememberScrollState()
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .verticalScroll(scroll)
        ) {
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                val color = Color(0xfff5ff77)
                PlayerStatInfo(width = 0.33f, type = "득점", value = playerInfo.points, color)
                PlayerStatInfo(width = 0.5f, type = "리바운드", value = playerInfo.rebound, color)
                PlayerStatInfo(width = 1f, type = "어시스트", value = playerInfo.assist, color)
            }
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                PlayerStatInfo(width = 0.5f, type = "등번호", value = playerInfo.jerseyNumber, Color.White)
                PlayerStatInfo(width = 1f, type = "포지션", value = playerInfo.position, Color.White)
            }
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                PlayerStatInfo(width = 0.5f, type = "키", value = "${playerInfo.height}cm", Color.White)
                PlayerStatInfo(width = 1f, type = "몸무게", value = "${playerInfo.weight}kg", Color.White)
            }
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                val draft = if (playerInfo.draftYear == "언드래프티") "언드래프티"
                else "${playerInfo.draftYear}년 ${playerInfo.draftRound}라운드 ${playerInfo.draftNumber}픽"
                PlayerStatInfo(width = 0.5f, type = "드래프트", value = draft, color)
                PlayerStatInfo(width = 1f, type = "소속", value = playerInfo.teamKoreanName, color)
            }
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                val college = if (playerInfo.college == "None") "미진학" else playerInfo.college
                PlayerStatInfo(width = 0.5f, type = "도시", value = playerInfo.country, color)
                PlayerStatInfo(width = 1f, type = "대학", value = college, color)
            }
        }
    }
}
@Composable
fun PlayerStatInfo(width: Float, type: String, value: String, color: Color) {
    Card(
        modifier = Modifier
            .fillMaxWidth(width)
            .padding(5.size()),
        shape = RoundedCornerShape(10.size()),
        elevation = 3.size()
    ) {
        Column(
            modifier = Modifier.background(color),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "", fontSize = 5.sp)

            Text(text = type, color = Color.Black,
                fontFamily = MainActivity.esamanru, fontWeight = FontWeight.Medium, fontSize = 20.sp)

            Text(text = value, color = Color.Black,
                fontFamily = MainActivity.esamanru, fontWeight = FontWeight.Medium, fontSize = 15.sp)

            Text(text = "", fontSize = 5.sp)
        }
    }
}