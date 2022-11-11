package io.github.cosineaa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.cosineaa.tracker.data.TeamInfo
import io.github.cosineaa.tracker.manager.TrackerManager
import kotlinx.coroutines.*
import org.json.simple.JSONArray

private lateinit var scope: CoroutineScope
private lateinit var tracker: TrackerManager
private lateinit var teamJson: JSONArray
private lateinit var teamList: List<TeamInfo>

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        scope = CoroutineScope(Dispatchers.IO)

        setContent {
            ConferenceView(scope)
        }
    }
}

@Preview
@Composable
fun ConferenceView(scope: CoroutineScope = rememberCoroutineScope()) {
    scope.launch {

    }
    tracker = TrackerManager()
    teamJson = tracker.getConferenceJson("WEST")
    //teamList = tracker.getConferenceRank(teamJson, false)
    Text("${teamJson}")
//    Column {
//        LazyColumn {
//            itemsIndexed(teamList) { index, item ->
//                ConferenceTeam(item)
//            }
//        }
//    }
}

@Composable
fun ConferenceTeam(info: TeamInfo) {
    Card(
        Modifier
            .padding(10.dp)
            .border(width = 3.dp, color = Color.Black)
            .fillMaxWidth()
            .height(50.dp))
    {
        Box(contentAlignment = Alignment.Center) {
            Text(text = "${info.conferenceRank} : ${info.teamName}")
        }
    }
}










