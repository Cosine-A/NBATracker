package io.github.cosineaa

import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import coil.compose.AsyncImage
import com.google.accompanist.pager.*
import io.github.cosineaa.MainActivity.Companion.allTeamList
import io.github.cosineaa.MainActivity.Companion.conferencePages
import io.github.cosineaa.MainActivity.Companion.eastTeamList
import io.github.cosineaa.MainActivity.Companion.esamanru
import io.github.cosineaa.MainActivity.Companion.mainInstance
import io.github.cosineaa.MainActivity.Companion.westTeamList
import io.github.cosineaa.tracker.data.TeamInfo
import io.github.cosineaa.tracker.manager.TrackerManager
import io.github.cosineaa.util.size
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.json.simple.JSONArray

class MainActivity : ComponentActivity() {

    companion object {
        lateinit var mainInstance: MainActivity
        lateinit var esamanru: FontFamily
        var dpi: Int = 420

        lateinit var tracker: TrackerManager
        lateinit var playerJson: JSONArray
        lateinit var eastTeamJson: JSONArray
        lateinit var westTeamJson: JSONArray
        lateinit var eastTeamList: List<TeamInfo>
        lateinit var westTeamList: List<TeamInfo>
        lateinit var allTeamList: List<TeamInfo>
        lateinit var conferencePages: List<String>
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        mainInstance = this
        super.onCreate(savedInstanceState)

        dpi = getDpi()

        esamanru = FontFamily(
            Font(R.font.esamanru_light, FontWeight.Light, FontStyle.Normal),
            Font(R.font.esamanru_medium, FontWeight.Medium, FontStyle.Normal),
            Font(R.font.esamanru_bold, FontWeight.Bold, FontStyle.Normal),
        )

        tracker = TrackerManager()
        playerJson = tracker.getPlayerJson()
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
    private fun getDpi(): Int {
        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        return metrics.densityDpi
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
        SelectConference(pageState, scope)
        ConferenceStatTypeGuide()
        ScrollConferenceTeam(pageState)
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun SelectConference(pageState: PagerState, scope: CoroutineScope) {
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
}

@Composable
fun ConferenceStatTypeGuide() {
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
            text = "순위", fontFamily = esamanru, fontWeight = FontWeight.Medium)
        Text(modifier = Modifier.fillMaxWidth(0.05f),
            text = "   ", fontFamily = esamanru, fontWeight = FontWeight.Medium)
        Text(modifier = Modifier.fillMaxWidth(0.25f),
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
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ScrollConferenceTeam(pageState: PagerState) {
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

@Composable
fun ConferenceTeam(order: Int, info: TeamInfo) {
    Card(
        modifier = Modifier
            .padding(2.size())
            .fillMaxWidth()
            .height(50.size())
            .clickable {
                val intent = Intent(mainInstance, ConferenceActivity::class.java)
                intent.putExtra("AllTeam", allTeamList.toCollection(ArrayList<TeamInfo>()))
                intent.putExtra("TeamInfo", info)
                mainInstance.startActivity(intent)
            },
        backgroundColor = Color.White,
        contentColor = Color.Black)
    {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Text(modifier = Modifier
                .fillMaxWidth(0.08f)
                .padding(start = 10.size()),
                text = String.format("%02d", order + 1), fontFamily = esamanru, fontWeight = FontWeight.Light)

            AsyncImage(modifier = Modifier
                .fillMaxWidth(0.14f)
                .size(40.size(), 40.size()),
                model = info.teamImage, contentDescription = info.teamKoreanShortName)
            // LoadConferenceTeamImage(teamShortName = info.teamShortName)

            Text(modifier = Modifier.fillMaxWidth(0.36f),
                text = info.teamKoreanShortName, fontFamily = esamanru, fontWeight = FontWeight.Light)

            Text(modifier = Modifier.fillMaxWidth(0.2f),
                text = info.gameAll, fontFamily = esamanru, fontWeight = FontWeight.Light)

            Text(modifier = Modifier.fillMaxWidth(0.2f),
                text = info.gameWin, fontFamily = esamanru, fontWeight = FontWeight.Light)

            Text(modifier = Modifier.fillMaxWidth(0.2f),
                text = info.gameLose, fontFamily = esamanru, fontWeight = FontWeight.Light)

            Text(modifier = Modifier.fillMaxWidth(0.6f),
                text = info.gameRate, fontFamily = esamanru, fontWeight = FontWeight.Light)

            Text(modifier = Modifier.fillMaxWidth(0.65f),
                text = info.gameContinuity, fontFamily = esamanru, fontWeight = FontWeight.Light)
        }
    }
}











