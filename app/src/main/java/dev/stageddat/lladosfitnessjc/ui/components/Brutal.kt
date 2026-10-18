package dev.stageddat.lladosfitnessjc.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.stageddat.lladosfitnessjc.ui.theme.ArchivoBlack
import dev.stageddat.lladosfitnessjc.ui.theme.Ink

// Caja neo-brutalism: fondo de color, borde negro grueso y sombra negra dura (sin difuminar)
fun Modifier.brutal(
    color: Color,
    shadow: Dp = 6.dp,
    radius: Dp = 12.dp,
    border: Dp = 3.dp
): Modifier = this
    // deja hueco para que la sombra no se salga
    .padding(end = shadow, bottom = shadow)
    // la sombra es el mismo rectángulo pintado en negro y desplazado
    .drawBehind {
        drawRoundRect(
            color = Ink,
            topLeft = Offset(shadow.toPx(), shadow.toPx()),
            size = size,
            cornerRadius = CornerRadius(radius.toPx())
        )
    }
    .background(color, RoundedCornerShape(radius))
    .border(border, Ink, RoundedCornerShape(radius))

// Tarjeta con una etiqueta de título arriba
@Composable
fun BrutalCard(
    title: String,
    color: Color,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .brutal(color)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Sticker(text = title, color = Color.White)
        content()
    }
}

// Etiqueta pequeña, un poco torcida, como una pegatina
@Composable
fun Sticker(text: String, color: Color, rotation: Float = -2f, fontSize: Int = 14) {
    Text(
        text = text.uppercase(),
        fontFamily = ArchivoBlack,
        fontSize = fontSize.sp,
        color = Ink,
        modifier = Modifier
            .rotate(rotation)
            .brutal(color, shadow = 3.dp, radius = 6.dp, border = 2.dp)
            .padding(horizontal = 10.dp, vertical = 4.dp)
    )
}

// Botón que se "hunde" sobre su sombra al pulsarlo
@Composable
fun BrutalButton(
    onClick: () -> Unit,
    color: Color,
    modifier: Modifier = Modifier,
    shadow: Dp = 6.dp,
    contentPadding: PaddingValues = PaddingValues(horizontal = 20.dp, vertical = 14.dp),
    content: @Composable RowScope.() -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()
    val pressOffset by animateDpAsState(if (pressed) shadow else 0.dp, label = "press")
    val shape = RoundedCornerShape(12.dp)

    // propagateMinConstraints: si al botón le dan tamaño (fillMaxWidth, size...) el Row lo ocupa entero
    Box(modifier = modifier, propagateMinConstraints = true) {
        // sombra
        Box(
            modifier = Modifier
                .matchParentSize()
                .padding(start = shadow, top = shadow)
                .background(Ink, shape)
        )
        // botón
        Row(
            modifier = Modifier
                .padding(end = shadow, bottom = shadow)
                .offset(x = pressOffset, y = pressOffset)
                .background(color, shape)
                .border(3.dp, Ink, shape)
                .clickable(interactionSource = interactionSource, indication = null, onClick = onClick)
                .padding(contentPadding),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            content = content
        )
    }
}
