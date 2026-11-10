package com.autopic.app

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.ImageSearch
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

private enum class AutoPicTab {
    LIBRARY, SEARCH, ASSISTANT, SETTINGS
}

data class PhotoAsset(
    val id: String,
    val title: String,
    val subtitle: String,
    val accent: Color,
    val score: Int,
    val enhanced: Boolean,
    val tags: List<String>
)

private val samplePhotos = listOf(
    PhotoAsset(
        id = "alpha",
        title = "Sunset Coast",
        subtitle = "Enhanced · 92% quality",
        accent = Color(0xFFFCB6A6),
        score = 92,
        enhanced = true,
        tags = listOf("landscape", "sunset")
    ),
    PhotoAsset(
        id = "bravo",
        title = "Garden Portrait",
        subtitle = "Portrait profile",
        accent = Color(0xFF9AD7FF),
        score = 88,
        enhanced = true,
        tags = listOf("portrait", "nature")
    ),
    PhotoAsset(
        id = "charlie",
        title = "Street Food",
        subtitle = "Color cast corrected",
        accent = Color(0xFFF7D66A),
        score = 84,
        enhanced = false,
        tags = listOf("food", "city")
    ),
    PhotoAsset(
        id = "delta",
        title = "Night Market",
        subtitle = "Low-light recovery",
        accent = Color(0xFF7C93FF),
        score = 81,
        enhanced = false,
        tags = listOf("night", "scene")
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AutoPicApp() {
    var selectedTab by remember { mutableStateOf(AutoPicTab.LIBRARY) }
    var query by remember { mutableStateOf("beach at sunset") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = when (selectedTab) {
                            AutoPicTab.LIBRARY -> "AutoPic Library"
                            AutoPicTab.SEARCH -> "Search"
                            AutoPicTab.ASSISTANT -> "AI Assistant"
                            AutoPicTab.SETTINGS -> "Settings"
                        },
                        fontWeight = FontWeight.SemiBold
                    )
                },
                actions = {
                    IconButton(onClick = { /* no-op */ }) {
                        Icon(Icons.Default.History, contentDescription = "Recent actions")
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar {
                AutoPicTab.entries.forEach { tab ->
                    NavigationBarItem(
                        selected = selectedTab == tab,
                        onClick = { selectedTab = tab },
                        icon = {
                            val icon = when (tab) {
                                AutoPicTab.LIBRARY -> Icons.Default.CameraAlt
                                AutoPicTab.SEARCH -> Icons.Default.Search
                                AutoPicTab.ASSISTANT -> Icons.Default.AutoAwesome
                                AutoPicTab.SETTINGS -> Icons.Default.Settings
                            }
                            Icon(icon, contentDescription = tab.name)
                        },
                        label = {
                            Text(
                                when (tab) {
                                    AutoPicTab.LIBRARY -> "Library"
                                    AutoPicTab.SEARCH -> "Search"
                                    AutoPicTab.ASSISTANT -> "Assistant"
                                    AutoPicTab.SETTINGS -> "Prefs"
                                }
                            )
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            color = MaterialTheme.colorScheme.background
        ) {
            when (selectedTab) {
                AutoPicTab.LIBRARY -> LibraryScreen()
                AutoPicTab.SEARCH -> SearchScreen(query = query, onQueryChange = { query = it })
                AutoPicTab.ASSISTANT -> AssistantScreen()
                AutoPicTab.SETTINGS -> SettingsScreen()
            }
        }
    }
}

@Composable
private fun LibraryScreen() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Enhanced", style = MaterialTheme.typography.titleMedium)
                Text("4 items", color = MaterialTheme.colorScheme.primary)
            }
        }

        items(samplePhotos) { photo ->
            PhotoCard(photo = photo)
        }
    }
}

@Composable
private fun PhotoCard(photo: PhotoAsset) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(92.dp)
                    .clip(RoundedCornerShape(18.dp))
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(photo.accent, Color(0xFF101828))
                        )
                    )
            ) {
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(8.dp)
                        .size(18.dp)
                        .clip(CircleShape)
                        .background(if (photo.enhanced) Color(0xFF34D399) else Color(0xFFFBBF24))
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(photo.title, style = MaterialTheme.typography.titleMedium)
                Text(photo.subtitle, style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    photo.tags.forEach { tag ->
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.12f))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(tag, style = MaterialTheme.typography.labelSmall)
                        }
                    }
                }
            }

            Column(horizontalAlignment = Alignment.End) {
                Text("${photo.score}%", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { /* preview */ }) {
                    Text("Enhance")
                }
            }
        }
    }
}

@Composable
private fun SearchScreen(
    query: String,
    onQueryChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = query,
            onValueChange = onQueryChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Search photos") }
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.ImageSearch, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Search engine", fontWeight = FontWeight.SemiBold)
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text("Offline text + visual search is active with a safe, private on-device pipeline.")
                Spacer(modifier = Modifier.height(12.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf("beach", "sunset", "portrait").forEach { term ->
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.12f))
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                                .clickable { onQueryChange(term) }
                        ) {
                            Text(term)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AssistantScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(Modifier.padding(16.dp)) {
                Text("Safe ask", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(8.dp))
                Text("I found 18 photos matching your query and can propose a safe enhancement recipe without altering originals.")
            }
        }

        listOf(
            "Enhance this set with a gentle portrait profile",
            "Copy all links from the QR folder",
            "Find similar photos to this one"
        ).forEach { suggestion ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.AutoAwesome, contentDescription = null)
                    Spacer(Modifier.width(12.dp))
                    Text(suggestion)
                }
            }
        }
    }
}

@Composable
private fun SettingsScreen() {
    var offlineMode by remember { mutableStateOf(true) }
    var aiConsent by remember { mutableStateOf(false) }
    var faceAnalysis by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SettingsToggle(title = "Offline mode", subtitle = "All core features stay on-device.", checked = offlineMode) {
            offlineMode = it
        }
        SettingsToggle(title = "AI assistant consent", subtitle = "Never act without user confirmation.", checked = aiConsent) {
            aiConsent = it
        }
        SettingsToggle(title = "Face analysis", subtitle = "Optional local-only categorization.", checked = faceAnalysis) {
            faceAnalysis = it
        }
    }
}

@Composable
private fun SettingsToggle(
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(title, fontWeight = FontWeight.SemiBold)
                Text(subtitle, style = MaterialTheme.typography.bodySmall)
            }
            Switch(checked = checked, onCheckedChange = onCheckedChange)
        }
    }
}
