package com.elkabsh.gamemeterbosta.feature.games.presentation.game_details.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

private val TAG_REGEX = Regex("</?[a-zA-Z][^>]*>")
private val NUMERIC_ENTITY_REGEX = Regex("&#(\\d+);")
private val NAMED_ENTITIES = mapOf(
    "&amp;" to "&",
    "&lt;" to "<",
    "&gt;" to ">",
    "&quot;" to "\"",
    "&#39;" to "'",
    "&apos;" to "'",
    "&nbsp;" to " "
)

private fun String.fromHtml(): AnnotatedString {
    val text = this
        .replace("<br />", "\n", ignoreCase = true)
        .replace("<br>", "\n", ignoreCase = true)
        .replace("</p>\n<p>", "\n\n", ignoreCase = true)
        .replace(TAG_REGEX, "")
    var result = NUMERIC_ENTITY_REGEX.replace(text) { match ->
        match.groupValues[1].toIntOrNull()?.toChar()?.toString() ?: match.value
    }
    NAMED_ENTITIES.forEach { (entity, replacement) -> result = result.replace(entity, replacement) }
    return AnnotatedString(result)
}

@Composable
fun AboutSection(
    description: String,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.animateContentSize()
    ) {
        Text(
            text = "About",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = description.fromHtml(),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = if (expanded) Int.MAX_VALUE else 5
        )

        TextButton(
            onClick = { expanded = !expanded }, modifier = Modifier.padding(top = 8.dp)
        ) {
            Text(
                text = if (expanded) "Read less" else "Read more",
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}