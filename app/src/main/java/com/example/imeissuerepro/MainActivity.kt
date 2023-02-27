package com.example.imeissuerepro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.imeissuerepro.ui.theme.IMEIssueReproTheme
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.lastOrNull

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IMEIssueReproTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    Reproduction()
                }
            }
        }
    }
}

@Composable
fun Reproduction() {
    var readOnly by remember { mutableStateOf(true) }
    var text by remember { mutableStateOf("Some text here") }
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()

    Column {
        Button(
            onClick = { readOnly = !readOnly },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = if (readOnly) "set readOnly = false" else "set readOnly = true")
        }
        BasicTextField(
            value = text,
            onValueChange = { text = it },
            interactionSource = interactionSource,
            readOnly = readOnly,
            modifier = Modifier.fillMaxSize()
        )
    }

    LaunchedEffect(pressed) {
        if (pressed) {
            readOnly = false
            return@LaunchedEffect
        }
        val interaction = interactionSource.interactions.last()
        println(interaction)
        if (interaction is PressInteraction.Release)
            readOnly = false
    }
}
