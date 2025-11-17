package com.example.anbcasestudyassigment.book.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Preview
@Composable
fun BookTabScreenPreview(){
    val tabMap = mapOf(
        "TabTest1" to arrayOf("TabsTest1 Desc", "TabsTest1 Desc", "TabsTest1 Desc", "TabsTest1 Desc", "TabsTest1 Desc", "TabsTest1 Desc"),
        "TabTest2" to arrayOf("TabTest2 Desc", "TabTest2 Desc", "TabTest2 Desc", "TabTest2 Desc", "TabTest2 Desc", "TabTest2 Desc"),
        "TabTest3" to arrayOf("TabTest3 Desc", "TabTest3 Desc", "TabTest3 Desc", "TabTest3 Desc", "TabTest3 Desc", "TabTest3 Desc"),
        "TabTest4" to arrayOf("TabTest4 Desc", "TabTest4 Desc", "TabTest4 Desc", "TabTest4 Desc", "TabTest4 Desc", "TabTest4 Desc"),
        "TabTest5" to arrayOf("TabTest5 Desc", "TabTest5 Desc", "TabTest5 Desc", "TabTest5 Desc", "TabTest5 Desc", "TabTest5 Desc")
    )
    val tabState = remember { mutableStateOf(tabMap) }
    BookTabScreen(tabState = tabState.value)

}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BookTabScreen(tabState : Map<String, Array<String>>){

    val pagerState = rememberPagerState { tabState.keys.size }

    val selectedTab = remember { mutableStateOf(0) }

    LaunchedEffect(selectedTab.value){
        pagerState.animateScrollToPage(selectedTab.value)
    }
    Scaffold (
        /*topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "BooksTabBar",
                        style = MaterialTheme.typography.headlineLarge,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Navigate back",
                            tint = Color.White,

                        )
                    }
                },
                actions = {}
            )
        },*/
        floatingActionButton = {
            FloatingActionButton(
                onClick = {},
                shape = FloatingActionButtonDefaults.largeShape,
                contentColor = Color.White,
                modifier = Modifier
                    .background(color = Color.Blue)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Icon"
                )
            }
        }
    ) {
        val tabTitle = tabState.keys.toList()
        Column {
        TabRow(
            selectedTabIndex = 0,
            containerColor = Color.LightGray,
            contentColor = Color.White,
            indicator = { tabPosition ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier
                        .tabIndicatorOffset(tabPosition[selectedTab.value]),
                    color = Color.Red,
                    height = 1.dp

                )
            }
        ) {
            tabTitle.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab.value == index,
                    onClick = {
                        selectedTab.value = index
                    },
                    text = { Text(text = title) },
                    selectedContentColor = Color.Black,
                    unselectedContentColor = Color.Red
                )
            }
        }

        Spacer(
            modifier = Modifier.height(2.dp)
        )

        HorizontalPager(
            state = pagerState
        ) { pageIndex ->
            val listState: LazyListState = rememberLazyListState()
            LazyColumn(
                state = listState,
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(
                    items = tabState.get(tabTitle[pageIndex])!!.toList(),
                    key = {
                        tabTitle[pageIndex] + pageIndex
                    }
                ) { item ->
                    Text(
                        text = item
                    )
                }
            }
        }
    }
    }
}


