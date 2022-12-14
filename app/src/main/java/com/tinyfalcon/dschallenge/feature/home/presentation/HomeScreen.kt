package com.tinyfalcon.dschallenge.feature.home.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.tinyfalcon.dschallenge.R
import com.tinyfalcon.dschallenge.feature.home.data.PageLoadingState
import com.tinyfalcon.dschallenge.feature.home.domain.SessionViewEntity
import com.tinyfalcon.dschallenge.style.DsGray
import com.tinyfalcon.dschallenge.style.DsLightGray
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    Column {
        DiscoverAndSearchArea(viewModel = viewModel)
        SessionList(lazySessionItems = viewModel.sessionItemsFlow)
    }
}

@Composable
fun DiscoverAndSearchArea(viewModel: HomeViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(DsGray)
    ) {
        DiscoverText()
        SearchArea(viewModel = viewModel)
    }
}

@Composable
fun DiscoverText() {
    Text(
        modifier = Modifier.padding(top = 72.dp, start = 16.dp),
        text = stringResource(id = R.string.discover),
        color = Color.White,
        style = MaterialTheme.typography.h4,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun SearchArea(viewModel: HomeViewModel) {
    Row(
        modifier = Modifier.padding(
            start = 16.dp,
            end = 16.dp,
            bottom = 12.dp,
            top = 16.dp
        )
    ) {
        SearchView(viewModel)
    }
}

@Composable
fun SearchView(viewModel: HomeViewModel) {
    val searchText by viewModel.searchText.collectAsState()
    viewModel.searchTextToQuery.collectAsState("")

    TextField(
        modifier = Modifier
            .background(DsLightGray)
            .fillMaxWidth(),
        value = searchText,
        onValueChange = {
            viewModel.searchText.value = it
        },
        placeholder = { Text(stringResource(id = R.string.search), color = Color.White) },
        leadingIcon = { Icon(Icons.Filled.Search, null, tint = Color.Yellow) },
        trailingIcon = { ProgressIndicator(viewModel.loadingState) },
        colors = TextFieldDefaults.textFieldColors(textColor = Color.White)
    )
}

private val span: (LazyGridItemSpanScope) -> GridItemSpan = { GridItemSpan(2) }

@Composable
fun SessionList(
    lazySessionItems: Flow<PagingData<SessionViewEntity>>
) {
    val lazyPagingItems = lazySessionItems.collectAsLazyPagingItems()

    LazyVerticalGrid(
        modifier = Modifier.padding(16.dp),
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(lazyPagingItems.itemCount) { index ->
            lazyPagingItems[index]?.let {
                SessionItem(session = it)
            }
        }
        renderLoading(lazyPagingItems.loadState)
    }
}

private fun LazyGridScope.renderLoading(loadState: CombinedLoadStates) {
    if (loadState.append !is LoadState.Loading) return
    item(span = span) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProgressIndicator(loadingState = MutableStateFlow(PageLoadingState.LOADING))
        }
    }
}

@Composable
fun SessionItem(session: SessionViewEntity) {
    Card(
        modifier = Modifier.fillMaxSize(),
        shape = RoundedCornerShape(8.dp)
    ) {
        val imageWidth = remember { mutableStateOf(0) }
        val imageHeight = remember { mutableStateOf(0) }

        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                modifier = Modifier
                    .matchParentSize()
                    .onGloballyPositioned {
                        imageWidth.value = it.size.width
                        imageHeight.value = it.size.height
                    },
                model = session.currentTrack?.artworkUrl,
                contentDescription = "",
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color(0xAA000000)),
                            startY = 0f,
                            endY = imageHeight.value.toFloat()
                        )
                    )
            )
            Column(
                modifier = Modifier
                    .height(200.dp)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SessionItemTextPart(session)
            }
            CountItem(this, session.listenerCount ?: 0)
        }
    }
}

@Composable
fun CountItem(boxScope: BoxScope, listenerCount: Int) {
    boxScope.run {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.TopStart)
                .background(shape = RoundedCornerShape(8.dp), color = Color(0xAAFFFFFF))
        ) {
            Image(
                modifier = Modifier.padding(2.dp),
                painter = painterResource(id = R.drawable.ic_baseline_headset_24),
                contentDescription = stringResource(id = R.string.counter)
            )
            Text(modifier = Modifier.padding(top = 2.dp, bottom = 2.dp, end = 2.dp), text = listenerCount.toString(), fontSize = 10.sp)
        }
    }
}

@Composable
fun SessionItemTextPart(session: SessionViewEntity) {
    Column {
        Text(
            text = "${session.name}",
            style = TextStyle(color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        )
        val builder = StringBuilder()
        session.genres?.forEachIndexed { index, genreText ->
            builder.append("${genreText}${if (index != session.genres.size - 1) ", " else ""}")
        }
        Text(
            text = builder.toString(),
            style = TextStyle(color = Color.White, fontSize = 12.sp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun ProgressIndicator(loadingState: StateFlow<PageLoadingState>) {
    val state = loadingState.collectAsState()
    AnimatedVisibility(visible = state.value == PageLoadingState.LOADING) {
        CircularProgressIndicator(modifier = Modifier.size(24.dp))
    }
}
