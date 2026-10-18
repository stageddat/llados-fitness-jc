package dev.stageddat.lladosfitnessjc.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp
import dev.stageddat.lladosfitnessjc.R

// Fuentes descargadas de Google Fonts
private val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

// Letra muy gorda para títulos y números
val ArchivoBlack = FontFamily(
    Font(googleFont = GoogleFont("Archivo Black"), fontProvider = provider)
)

// Letra para el resto de textos
val SpaceGrotesk = FontFamily(
    Font(googleFont = GoogleFont("Space Grotesk"), fontProvider = provider, weight = FontWeight.Medium),
    Font(googleFont = GoogleFont("Space Grotesk"), fontProvider = provider, weight = FontWeight.Bold)
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = SpaceGrotesk,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)
