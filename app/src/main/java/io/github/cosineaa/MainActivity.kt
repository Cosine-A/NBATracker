package io.github.cosineaa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import io.github.cosineaa.tracker.data.TeamInfo
import io.github.cosineaa.tracker.manager.TrackerManager
import kotlinx.coroutines.launch
import org.json.simple.JSONArray


private lateinit var tracker: TrackerManager

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tracker = TrackerManager()

        setContent {
            ConferenceView()
        }
    }
}

private lateinit var eastTeamJson: JSONArray
private lateinit var westTeamJson: JSONArray
private lateinit var eastTeamList: List<TeamInfo>
private lateinit var westTeamList: List<TeamInfo>
private lateinit var allTeamList: List<TeamInfo>
private val conferencePages = listOf("모두", "동부", "서부")

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ConferenceView() {
    val pageState = rememberPagerState()
    val scope = rememberCoroutineScope()

    eastTeamJson = tracker.getConferenceJson("EAST")
    westTeamJson = tracker.getConferenceJson("WEST")
    eastTeamList = tracker.getConferenceRank(eastTeamJson, false)
    westTeamList = tracker.getConferenceRank(westTeamJson, false)
    allTeamList = tracker.getAllConferenceRank(eastTeamJson, westTeamJson, false)

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
                    text = { Text(text = title, color = Color.White) },
                    selected = pageState.currentPage == index,
                    onClick = {
                        scope.launch {
                            pageState.scrollToPage(index)
                        }
                    }
                )
            }
        }

        // Spacer(Modifier.size(100.dp))

        Row(horizontalArrangement = Arrangement.Center) {
            Text(text = "팀")
            Text(text = "승")
            Text(text = "패")
        }

        HorizontalPager(count = conferencePages.size, state = pageState) {
            LazyColumn {
                when (pageState.currentPage) {
                    0 -> itemsIndexed(allTeamList) { index, item -> ConferenceTeam(index, item) }
                    1 -> itemsIndexed(eastTeamList) { index, item -> ConferenceTeam(index, item) }
                    2 -> itemsIndexed(westTeamList) { index, item -> ConferenceTeam(index, item) }
                }
            }
        }
    }
}

@Composable
fun ConferenceTopAppBar() {
    TopAppBar(
        title = { Text(text = "컨퍼런스", color = Color.White) },
        backgroundColor = Color.Black
    )
}

@Composable
fun ConferenceTeam(order: Int, info: TeamInfo) {
    Card(
        Modifier
            .border(width = 1.dp, color = Color.Black)
            .fillMaxWidth()
            .height(50.dp)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(text = "${order + 1} : ${info.teamName} / ${info.gameRate}")
        }
    }
}










