package com.dicoding.pompomwikie.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.dicoding.pompomwikie.R


object AppFont {
    val poppinsFamily = FontFamily(
        Font(R.font.poppins_regular),
        Font(R.font.poppins_italic, style = FontStyle.Italic),
        Font(R.font.poppins_medium, FontWeight.Medium),
        Font(R.font.poppins_semibold, FontWeight.SemiBold),
        Font(R.font.poppins_bold, FontWeight.Bold),
    )
}

private val defaultTypography = Typography()

// Set of Material typography styles to start with
val Typography = Typography(

    titleLarge = defaultTypography.titleLarge.copy(
        fontFamily = AppFont.poppinsFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    ),
    titleMedium = defaultTypography.titleMedium.copy(
        fontFamily = AppFont.poppinsFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp
    ),
    titleSmall = defaultTypography.titleSmall.copy(
        fontFamily = AppFont.poppinsFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp
    ),

    bodyLarge = defaultTypography.bodyLarge.copy(
        fontFamily = AppFont.poppinsFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp
    ),
    bodyMedium = defaultTypography.bodyMedium.copy(
        fontFamily = AppFont.poppinsFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp
    ),
    bodySmall = defaultTypography.bodySmall.copy(
        fontFamily = AppFont.poppinsFamily,
        fontSize = 12.sp
    ),
)