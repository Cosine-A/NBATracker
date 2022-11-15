package io.github.cosineaa

import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import io.github.cosineaa.MainActivity.Companion.allTeamList
import io.github.cosineaa.MainActivity.Companion.esamanru
import io.github.cosineaa.MainActivity.Companion.mainInstance
import io.github.cosineaa.tracker.data.TeamInfo
import io.github.cosineaa.tracker.manager.TrackerManager
import io.github.cosineaa.util.size
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
fun MainView() {
    Column {
        MainButton(height = 0.5f, value = "선수", true)
        MainButton(height = 1f, value = "컨퍼런스", false)
    }
}
@Composable
fun MainButton(height: Float, value: String, select: Boolean) {
    Card(
        modifier = Modifier
            .fillMaxHeight(height)
            .fillMaxWidth()
            .padding(10.size())
            .clickable {
                val intent: Intent
                if (select) { // 선수
                    intent = Intent(mainInstance, ConferenceActivity::class.java)
                    mainInstance.startActivity(intent)
                } else { // 컨퍼런스
                    intent = Intent(mainInstance, ConferenceActivity::class.java)
                    mainInstance.startActivity(intent)
                }
            },
        shape = RoundedCornerShape(10.size()),
        elevation = 5.size(),
    ) {
        Box(
            modifier = Modifier.background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = value, textAlign = TextAlign.Center, color = Color.Black,
                fontFamily = esamanru, fontWeight = FontWeight.Medium, fontSize = 70.sp
            )
        }
    }
}










