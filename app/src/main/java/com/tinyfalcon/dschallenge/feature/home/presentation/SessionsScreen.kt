package com.tinyfalcon.dschallenge.feature.home.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.tinyfalcon.dschallenge.feature.home.data.Session
import com.tinyfalcon.dschallenge.feature.home.domain.SessionViewEntity
import kotlinx.coroutines.flow.Flow

@Composable
fun SessionsScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val lazySessionItems = viewModel.notificationsFlow.collectAsLazyPagingItems()

    Column {
        DiscoverText()
        TextField(value = "Search here", onValueChange = {})
        Button(onClick = {
            viewModel.searchState()
            lazySessionItems.refresh()
        }) {
            Text(text = "Search!")
        }

        SessionList(lazySessionItems)
    }
}

@Composable
fun DiscoverText() {
    Text(text = "Discover")
}

@Composable
fun SessionList(lazySessionItems: LazyPagingItems<SessionViewEntity>) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(lazySessionItems.itemCount) { index ->
            lazySessionItems[index]?.let {
                SessionItem(session = it)
            }
        }
    }
}

@Composable
fun SessionItem(session: SessionViewEntity) {
    Card(
        modifier = Modifier.fillMaxSize(),
        shape = RoundedCornerShape(8.dp),
        backgroundColor = MaterialTheme.colors.secondary,
    ) {
        Box {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = session.currentTrack?.artworkUrl,
                contentDescription = ""
            )
            Box(modifier = Modifier
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(Color.White, Color.Red)
                    )
                )
                .fillMaxSize())
            Column(
                modifier = Modifier
                    .height(200.dp)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SessionItemTextPart(session)
            }
        }
    }
}

@Composable
fun SessionItemTextPart(session: SessionViewEntity) {
    Column {
        Text(text = "${session.name}", style = MaterialTheme.typography.h6)

        val builder = StringBuilder()
        session.genres?.forEach { genre ->
            builder.append("${genre}, ")
        }
        Text(text = builder.toString(), style = MaterialTheme.typography.subtitle2)
    }
}