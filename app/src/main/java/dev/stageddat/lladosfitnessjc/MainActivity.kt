package dev.stageddat.lladosfitnessjc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.stageddat.lladosfitnessjc.ui.components.BrutalButton
import dev.stageddat.lladosfitnessjc.ui.components.BrutalCard
import dev.stageddat.lladosfitnessjc.ui.components.Sticker
import dev.stageddat.lladosfitnessjc.ui.components.brutal
import dev.stageddat.lladosfitnessjc.ui.theme.ArchivoBlack
import dev.stageddat.lladosfitnessjc.ui.theme.BrutalBlue
import dev.stageddat.lladosfitnessjc.ui.theme.BrutalGreen
import dev.stageddat.lladosfitnessjc.ui.theme.BrutalOrange
import dev.stageddat.lladosfitnessjc.ui.theme.BrutalPink
import dev.stageddat.lladosfitnessjc.ui.theme.BrutalRed
import dev.stageddat.lladosfitnessjc.ui.theme.BrutalYellow
import dev.stageddat.lladosfitnessjc.ui.theme.Ink
import dev.stageddat.lladosfitnessjc.ui.theme.LladosFitnessJCTheme
import dev.stageddat.lladosfitnessjc.ui.theme.SpaceGrotesk

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // fondo claro siempre: iconos de la barra de estado en negro
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(android.graphics.Color.TRANSPARENT, android.graphics.Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.light(android.graphics.Color.TRANSPARENT, android.graphics.Color.TRANSPARENT)
        )
        setContent {
            LladosFitnessJCTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    BMIScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

// Categoría del BMI: nombre, color y comentario de Llados
data class BmiCategory(val label: String, val color: Color, val comment: String)

fun getBmiCategory(bmi: Float): BmiCategory {
    return when {
        bmi < 18.5f -> BmiCategory("Bajo peso", BrutalBlue, "estás más seco que tu cuenta bancaria. Eres un mileurista, un pringado y un mediocre.")
        bmi < 25f -> BmiCategory("Normal", BrutalGreen, "estás normal. Y normal es para pringados. Compra mi nuevo curso por solo 999 €/mes y gana 100k al mes.")
        bmi < 30f -> BmiCategory("Sobrepeso", BrutalOrange, "estás gordo. Puto fracasado. Levanta ese culo de mierda, puto perdedor.")
        else -> BmiCategory("Obesidad", BrutalRed, "no he visto ninguna panza más grande que la tuya. Solo tu peso tiene su propio campo gravitatorio y un poco más y te conviertes en un agujero negro de fracasos.")
    }
}

@Composable
fun BMIScreen(modifier: Modifier = Modifier) {
    var name:String by remember { mutableStateOf("") }
    var weight:Int by remember { mutableStateOf(80) }
    var height:Int by remember { mutableStateOf(180) }
    var bmi:Float by remember { mutableStateOf(0f) }
    val focusManager = LocalFocusManager.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // cabecera
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = "LLADOS\nFITNESS",
                fontFamily = ArchivoBlack,
                fontSize = 48.sp,
                lineHeight = 48.sp
            )
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
                Sticker(text = "Pro+", color = BrutalYellow, rotation = -4f, fontSize = 20)
                Sticker(text = "El éxito se fabrica", color = BrutalPink, rotation = 2f)
            }
        }

        // nombre
        BrutalCard(title = "Nombre", color = BrutalYellow) {
            BasicTextField(
                value = name,
                onValueChange = { name = it },
                singleLine = true,
                textStyle = TextStyle(fontFamily = SpaceGrotesk, fontSize = 18.sp, color = Ink),
                cursorBrush = SolidColor(Ink),
                modifier = Modifier
                    .fillMaxWidth()
                    .brutal(Color.White, shadow = 4.dp, radius = 8.dp),
                decorationBox = { innerTextField ->
                    Box(modifier = Modifier.padding(14.dp)) {
                        if (name.isEmpty()) {
                            Text(text = "Amadeo Lladós", fontSize = 18.sp, color = Ink.copy(alpha = 0.4f))
                        }
                        innerTextField()
                    }
                }
            )
        }

        // peso
        BrutalCard(title = "Peso", color = BrutalBlue) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                BigNumber(value = "$weight", unit = "kg", modifier = Modifier.weight(1f))
                BrutalButton(
                    onClick = { if (weight > 1) weight-- },
                    color = Color.White,
                    modifier = Modifier.size(62.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(text = "−", fontFamily = ArchivoBlack, fontSize = 28.sp)
                }
                BrutalButton(
                    onClick = { weight++ },
                    color = BrutalYellow,
                    modifier = Modifier.padding(start = 8.dp).size(62.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(text = "+", fontFamily = ArchivoBlack, fontSize = 28.sp)
                }
            }
        }

        // altura
        BrutalCard(title = "Altura", color = BrutalGreen) {
            BigNumber(value = "$height", unit = "cm")
            BrutalSlider(
                value = height,
                onValueChange = { height = it },
                min = 100,
                max = 250
            )
            Row {
                Text(text = "100 cm", modifier = Modifier.weight(1f))
                Text(text = "250 cm")
            }
        }

        BrutalButton(
            onClick = {
                focusManager.clearFocus()
                var heightMeters:Float = height.toFloat() / 100
                bmi = (weight / (heightMeters * heightMeters))
            },
            color = BrutalPink,
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(vertical = 18.dp)
        ) {
            Text(text = "CALCULAR", fontFamily = ArchivoBlack, fontSize = 22.sp)
        }

        // resultado: aparece con animación cuando hay BMI
        AnimatedVisibility(
            visible = bmi != 0f,
            enter = fadeIn() + slideInVertically { it / 2 }
        ) {
            ResultCard(name = name.ifBlank { "Llados" }, bmi = bmi)
        }
    }
}

// número enorme + unidad pequeña
@Composable
fun BigNumber(value: String, unit: String, modifier: Modifier = Modifier) {
    Row(modifier = modifier, verticalAlignment = Alignment.Bottom) {
        Text(text = value, fontFamily = ArchivoBlack, fontSize = 48.sp, lineHeight = 48.sp)
        Text(
            text = unit,
            fontFamily = ArchivoBlack,
            fontSize = 20.sp,
            modifier = Modifier.padding(start = 6.dp, bottom = 8.dp)
        )
    }
}

// Slider con pista y bola al estilo neo-brutalism
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BrutalSlider(value: Int, onValueChange: (Int) -> Unit, min: Int, max: Int) {
    val fraction = (value - min).toFloat() / (max - min)
    Slider(
        value = value.toFloat(),
        onValueChange = { onValueChange(it.toInt()) },
        valueRange = min.toFloat()..max.toFloat(),
        thumb = {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .brutal(BrutalYellow, shadow = 3.dp, radius = 17.dp)
            )
        },
        track = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(18.dp)
                    .background(Color.White, RoundedCornerShape(9.dp))
                    .border(3.dp, Ink, RoundedCornerShape(9.dp))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(fraction)
                        .fillMaxHeight()
                        .background(Ink, RoundedCornerShape(9.dp))
                )
            }
        }
    )
}

@Composable
fun ResultCard(name: String, bmi: Float) {
    val category = getBmiCategory(bmi)
    BrutalCard(title = "Resultado", color = category.color) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "%.2f".format(bmi),
                fontFamily = ArchivoBlack,
                fontSize = 56.sp,
                lineHeight = 56.sp,
                modifier = Modifier.weight(1f)
            )
            Sticker(text = category.label, color = Color.White, rotation = 3f, fontSize = 16)
        }
        BmiScale(bmi = bmi)
        Text(text = "$name, ${category.comment}", fontSize = 17.sp)
    }
}

// Barra de colores con una flecha donde cae tu BMI (de 15 a 40)
@Composable
fun BmiScale(bmi: Float) {
    val minBmi = 15f
    val maxBmi = 40f
    val target = (bmi.coerceIn(minBmi, maxBmi) - minBmi) / (maxBmi - minBmi)
    // la flecha se desliza hasta su sitio
    val fraction by animateFloatAsState(target, label = "scale")

    BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
        // la barra pierde 3dp por la sombra
        val barWidth = maxWidth - 3.dp
        Column {
            Text(
                text = "▼",
                fontSize = 20.sp,
                lineHeight = 20.sp,
                modifier = Modifier.offset(x = barWidth * fraction - 8.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(25.dp)
                    .brutal(Color.White, shadow = 3.dp, radius = 6.dp)
                    .padding(3.dp)
            ) {
                // anchos proporcionales a cada rango: 15-18.5, 18.5-25, 25-30, 30-40
                ScaleSegment(BrutalBlue, 3.5f)
                ScaleSegment(BrutalGreen, 6.5f)
                ScaleSegment(BrutalOrange, 5f)
                ScaleSegment(BrutalRed, 10f)
            }
        }
    }
}

@Composable
fun RowScope.ScaleSegment(color: Color, weight: Float) {
    Box(
        modifier = Modifier
            .weight(weight)
            .fillMaxHeight()
            .background(color)
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LladosFitnessJCTheme {
        BMIScreen()
    }
}
