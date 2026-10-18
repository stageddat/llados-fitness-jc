package dev.stageddat.lladosfitnessjc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.stageddat.lladosfitnessjc.ui.theme.LladosFitnessJCTheme

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LladosFitnessJCTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    BMIScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun BMIScreen(modifier: Modifier = Modifier) {
    var name:String by remember { mutableStateOf("") }
    var weight:Int by remember { mutableStateOf(80) }
    var height:Int by remember { mutableStateOf(180) }
    var bmi:Float by remember { mutableStateOf(0f) }

    Column(modifier = modifier) {
        Text(text = "Llados Fitness Pro+",
           )
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombre") }
        )
        Text(text = "Peso")
        Row() {
            Text(text = "$weight Kg")
            Button(onClick = {
                if (weight > 1) weight--
            }) {
                Text(text = "-")
            }
            Button(
                onClick = { weight++ },
            ) {
                Text(
                    text = "+"
                )
            }
        }
        Text(text = "Altura: $height cm")
        Slider(
            value = height.toFloat(),
            onValueChange = { height = it.toInt() },
            valueRange = 100f..250f
        )
        Button(
            onClick = {
                var heightMeters:Float = height.toFloat() / 100
                bmi = (weight / (heightMeters * heightMeters))
            }
        ) {
            Text(text = "Calcular")
        }
        if(bmi != 0f) {
            Text(text = "${name.ifBlank { "Llados" }}, tu BMI es ${"%.2f".format(bmi)}")
        }

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LladosFitnessJCTheme {
        BMIScreen()
    }
}
