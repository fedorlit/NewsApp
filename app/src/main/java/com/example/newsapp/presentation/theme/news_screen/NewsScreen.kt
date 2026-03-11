package com.example.newsapp.presentation.theme.news_screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.newsapp.presentation.theme.component.NewsArticleCard
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.example.newsapp.domain.model.Article
import com.example.newsapp.presentation.theme.component.BottomSheetContent
import com.example.newsapp.presentation.theme.component.CategoryTabRow
import com.example.newsapp.presentation.theme.component.NewsScreenTopBar
import com.example.newsapp.presentation.theme.component.RetryContent
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class,ExperimentalFoundationApi::class)
@Composable
fun NewsScreen(
    //viewModel: NewsScreenViewModel = hiltViewModel()
    state: NewsScreenState,
    onEvent: (NewsScreenEvent) -> Unit,
    onReadFullStoryButtonClicked:(String)->Unit
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val coroutineScope= rememberCoroutineScope()
    val categories = listOf(
        "General", "Business", "Health", "Science", "Sports", "Technology", "Entertainment"
    )
    val pagerState = rememberPagerState(pageCount = {categories.size})

    val sheetState= rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var shouldBottomSheetShow by remember { mutableStateOf(false) }

    if(shouldBottomSheetShow){
        ModalBottomSheet(
            onDismissRequest = {shouldBottomSheetShow=false },
            sheetState=sheetState,
            content ={
                state.selectedArticle?.let {
                    BottomSheetContent(
                        article = it,
                        onReadFullStoryButtonClicked = {
                            onReadFullStoryButtonClicked(it.url)
                            coroutineScope.launch { sheetState.hide() }.invokeOnCompletion {
                                if(!sheetState.isVisible) shouldBottomSheetShow=false
                            }
                        }
                    )
                }
            }
        )
    }

    LaunchedEffect(key1 = pagerState){
        snapshotFlow { pagerState.currentPage }.collect{page->
            onEvent(NewsScreenEvent.OnCategoryChanged(category = categories[page]))
        }
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            NewsScreenTopBar(
                scrollBehavior=scrollBehavior,
                //onSearchIconClicked = {}
            )
        }
    ) { padding ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ){
            CategoryTabRow(
                pagerState = pagerState,
                categories = categories,
                onTabSelected = { index ->
                    coroutineScope.launch { pagerState.animateScrollToPage(index) }
                }
            )
            HorizontalPager(
                state = pagerState
            ) {
                NewsArticclesList(
                    state = state,
                    onCardClicked = { article ->
                        shouldBottomSheetShow=true
                        onEvent(NewsScreenEvent.OnNewsCardClicked(article=article)) },
                    onRetry = {
                        onEvent(NewsScreenEvent.OnCategoryChanged(state.category))
                    }
                )
            }

        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NewsArticclesList(
    state: NewsScreenState,
    onCardClicked:(Article)-> Unit,
    onRetry:()-> Unit
){
    LazyColumn (
        verticalArrangement=Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(state.articles){article->
            NewsArticleCard(
                article = article,
                onCardClicked = {onCardClicked(article)}
            )
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        if(state.isLoading){
            CircularProgressIndicator()
        }
        if(state.error!=null){
            RetryContent(
                error = state.error,
                onRetry = onRetry
            )
        }
    }
}