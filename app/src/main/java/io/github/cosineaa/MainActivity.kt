package io.github.cosineaa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import io.github.cosineaa.tracker.data.TeamInfo
import io.github.cosineaa.tracker.manager.TrackerManager
import kotlinx.coroutines.launch
import org.json.simple.JSONArray

private lateinit var tracker: TrackerManager
private lateinit var eastTeamJson: JSONArray
private lateinit var westTeamJson: JSONArray
private lateinit var eastTeamList: List<TeamInfo>
private lateinit var westTeamList: List<TeamInfo>
private lateinit var allTeamList: List<TeamInfo>
private lateinit var conferencePages: List<String>
private lateinit var esamanru: FontFamily

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        esamanru = FontFamily(
            Font(R.font.esamanru_light, FontWeight.Light, FontStyle.Normal),
            Font(R.font.esamanru_medium, FontWeight.Medium, FontStyle.Normal),
            Font(R.font.esamanru_bold, FontWeight.Bold, FontStyle.Normal),
        )

        tracker = TrackerManager()
        eastTeamJson = tracker.getConferenceJson("EAST")
        westTeamJson = tracker.getConferenceJson("WEST")
        eastTeamList = tracker.getConferenceRank(eastTeamJson, false)
        westTeamList = tracker.getConferenceRank(westTeamJson, false)
        allTeamList = tracker.getAllConferenceRank(eastTeamJson, westTeamJson, false)
        conferencePages = listOf("모두", "동부", "서부")

        setContent {
            MainView()
        }
    }
}

@Composable
fun ConferenceTopAppBar() {
    TopAppBar(
        title = { Text(text = "컨퍼런스", color = Color.White, fontFamily = esamanru, fontWeight = FontWeight.Bold) },
        backgroundColor = Color.Black
    )
}

@Composable
fun MainView() {
    Box {
        ConferenceView()
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ConferenceView() {
    val pageState = rememberPagerState()
    val scope = rememberCoroutineScope()

    Column {
        ConferenceTopAppBar()
        TabRow(
            selectedTabIndex = pageState.currentPage,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(Modifier.pagerTabIndicatorOffset(pageState, tabPositions)) },
            backgroundColor = Color.Black,
            contentColor = Color.Yellow
        ) {
            conferencePages.forEachIndexed { index, title ->
                Tab(
                    text = { Text(text = title, color = Color.White, fontFamily = esamanru, fontWeight = FontWeight.Medium) },
                    selected = pageState.currentPage == index,
                    onClick = {
                        scope.launch {
                            pageState.scrollToPage(index)
                        }
                    }
                )
            }
        }

        Row(
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
                .height(30.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly)
        {
            Text(
                modifier = Modifier.fillMaxWidth(0.07f),
                text = "순위", fontFamily = esamanru, fontWeight = FontWeight.Medium)
            Text(modifier = Modifier.fillMaxWidth(0.11f),
                text = "   ", fontFamily = esamanru, fontWeight = FontWeight.Medium)
            Text(modifier = Modifier.fillMaxWidth(0.13f),
                text = "팀", fontFamily = esamanru, fontWeight = FontWeight.Medium)
            Text(modifier = Modifier.fillMaxWidth(0.11f),
                text = "경기", fontFamily = esamanru, fontWeight = FontWeight.Medium)
            Text(modifier = Modifier.fillMaxWidth(0.07f),
                text = "승", fontFamily = esamanru, fontWeight = FontWeight.Medium)
            Text(modifier = Modifier.fillMaxWidth(0.08f),
                text = "패", fontFamily = esamanru, fontWeight = FontWeight.Medium)
            Text(modifier = Modifier.fillMaxWidth(0.18f),
                text = "승률", fontFamily = esamanru, fontWeight = FontWeight.Medium)
            Text(modifier = Modifier.fillMaxWidth(0.2f),
                text = "연속", fontFamily = esamanru, fontWeight = FontWeight.Medium)
        }

        HorizontalPager(count = conferencePages.size, state = pageState) { page ->
            LazyColumn {
                when (page) {
                    0 -> itemsIndexed(allTeamList) { index, item -> ConferenceTeam(index, item) }
                    1 -> itemsIndexed(eastTeamList) { index, item -> ConferenceTeam(index, item) }
                    2 -> itemsIndexed(westTeamList) { index, item -> ConferenceTeam(index, item) }
                }
            }
        }
    }
}

@Composable
fun ConferenceTeam(order: Int, info: TeamInfo) {
    Card(
        modifier = Modifier
            .padding(2.dp)
            .fillMaxWidth()
            .height(50.dp),
        backgroundColor = Color.White,
        contentColor = Color.Black)
    {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Text(modifier = Modifier.fillMaxWidth(0.11f).padding(start = 20.dp),
                text = String.format("%02d", order + 1), fontFamily = esamanru, fontWeight = FontWeight.Light)

            AsyncImage(modifier = Modifier.fillMaxWidth(0.2f).size(40.dp, 40.dp),
                model = info.teamImage, contentDescription = info.teamShortName)
            // LoadConferenceTeamImage(teamShortName = info.teamShortName)

            Text(modifier = Modifier.fillMaxWidth(0.25f),
                text = info.teamShortName, fontFamily = esamanru, fontWeight = FontWeight.Light)

            Text(modifier = Modifier.fillMaxWidth(0.2f),
                text = info.gameAll, fontFamily = esamanru, fontWeight = FontWeight.Light)

            Text(modifier = Modifier.fillMaxWidth(0.2f),
                text = info.gameWin, fontFamily = esamanru, fontWeight = FontWeight.Light)

            Text(modifier = Modifier.fillMaxWidth(0.2f),
                text = info.gameLose, fontFamily = esamanru, fontWeight = FontWeight.Light)

            Text(modifier = Modifier.fillMaxWidth(0.6f),
                text = info.gameRate, fontFamily = esamanru, fontWeight = FontWeight.Light)

            Text(modifier = Modifier.fillMaxWidth(0.7f),
                text = info.gameContinuity, fontFamily = esamanru, fontWeight = FontWeight.Light)
        }
    }
}

@Composable
fun LoadConferenceTeamImage(teamShortName: String) {
    val modifier = Modifier.size(40.dp, 40.dp)
    if (teamShortName == "MIL")
        Image(painter = painterResource(id = R.drawable.mil), modifier = modifier, contentDescription = teamShortName)
    if (teamShortName == "UTA")
        Image(painter = painterResource(id = R.drawable.uta), modifier = modifier, contentDescription = teamShortName)
    if (teamShortName == "POR")
        Image(painter = painterResource(id = R.drawable.por), modifier = modifier, contentDescription = teamShortName)
    if (teamShortName == "BOS")
        Image(painter = painterResource(id = R.drawable.bos), modifier = modifier, contentDescription = teamShortName)
    if (teamShortName == "MEM")
        Image(painter = painterResource(id = R.drawable.mem), modifier = modifier, contentDescription = teamShortName)
    if (teamShortName == "DEN")
        Image(painter = painterResource(id = R.drawable.den), modifier = modifier, contentDescription = teamShortName)
    if (teamShortName == "PHX")
        Image(painter = painterResource(id = R.drawable.phx), modifier = modifier, contentDescription = teamShortName)
    if (teamShortName == "CLE")
        Image(painter = painterResource(id = R.drawable.cle), modifier = modifier, contentDescription = teamShortName)
    if (teamShortName == "ATL")
        Image(painter = painterResource(id = R.drawable.atl), modifier = modifier, contentDescription = teamShortName)
    if (teamShortName == "LAC")
        Image(painter = painterResource(id = R.drawable.lac), modifier = modifier, contentDescription = teamShortName)
    if (teamShortName == "DAL")
        Image(painter = painterResource(id = R.drawable.dal), modifier = modifier, contentDescription = teamShortName)
    if (teamShortName == "TOR")
        Image(painter = painterResource(id = R.drawable.tor), modifier = modifier, contentDescription = teamShortName)
    if (teamShortName == "NOP")
        Image(painter = painterResource(id = R.drawable.nop), modifier = modifier, contentDescription = teamShortName)
    if (teamShortName == "NYK")
        Image(painter = painterResource(id = R.drawable.nyk), modifier = modifier, contentDescription = teamShortName)
    if (teamShortName == "WAS")
        Image(painter = painterResource(id = R.drawable.was), modifier = modifier, contentDescription = teamShortName)
    if (teamShortName == "SAS")
        Image(painter = painterResource(id = R.drawable.sas), modifier = modifier, contentDescription = teamShortName)
    if (teamShortName == "CHI")
        Image(painter = painterResource(id = R.drawable.chi), modifier = modifier, contentDescription = teamShortName)
    if (teamShortName == "SAC")
        Image(painter = painterResource(id = R.drawable.sac), modifier = modifier, contentDescription = teamShortName)
    if (teamShortName == "IND")
        Image(painter = painterResource(id = R.drawable.ind), modifier = modifier, contentDescription = teamShortName)
    if (teamShortName == "OKC")
        Image(painter = painterResource(id = R.drawable.okc), modifier = modifier, contentDescription = teamShortName)
    if (teamShortName == "GSW")
        Image(painter = painterResource(id = R.drawable.gsw), modifier = modifier, contentDescription = teamShortName)
    if (teamShortName == "PHI")
        Image(painter = painterResource(id = R.drawable.phi), modifier = modifier, contentDescription = teamShortName)
    if (teamShortName == "MIA")
        Image(painter = painterResource(id = R.drawable.mia), modifier = modifier, contentDescription = teamShortName)
    if (teamShortName == "BKN")
        Image(painter = painterResource(id = R.drawable.bkn), modifier = modifier, contentDescription = teamShortName)
    if (teamShortName == "MIN")
        Image(painter = painterResource(id = R.drawable.min), modifier = modifier, contentDescription = teamShortName)
    if (teamShortName == "ORL")
        Image(painter = painterResource(id = R.drawable.orl), modifier = modifier, contentDescription = teamShortName)
    if (teamShortName == "DET")
        Image(painter = painterResource(id = R.drawable.det), modifier = modifier, contentDescription = teamShortName)
    if (teamShortName == "CHA")
        Image(painter = painterResource(id = R.drawable.cha), modifier = modifier, contentDescription = teamShortName)
    if (teamShortName == "HOU")
        Image(painter = painterResource(id = R.drawable.hou), modifier = modifier, contentDescription = teamShortName)
    if (teamShortName == "LAL")
        Image(painter = painterResource(id = R.drawable.lal), modifier = modifier, contentDescription = teamShortName)
}









