@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.anbcasestudyassigment.book.presentation.book_list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.anbcasestudyassigment.books.domain.Book
import com.example.anbcasestudyassigment.books.presentation.book_list.components.BookList
import kotlinx.coroutines.flow.collect

@Composable
fun BookListScreenRoot(
    viewModel: BookListViewModel = hiltViewModel(),
    onBookClick: (book: Book) -> Unit,
    modifier: Modifier = Modifier
        .clickable{
            viewModel.onAction(BookListScreenActions.onRefresh(isRefresh = true))
        }
) {
    val state = viewModel.bookListState.collectAsState()

    LaunchedEffect(state.value.page)  {
       viewModel.bookListScreenEvents.collect() {
           when(it){
               is BookListScreenEvents.showToast -> {

               }

               is BookListScreenEvents.navigateToBookDetails -> {
                   onBookClick(it.book)
               }

               else -> {

               }
           }
       }
   }

    if (state.value.books.isNotEmpty()) {
            BookListScreen(
                state = state.value,
                onAction = { action ->
                    viewModel.onAction(action)
                },
                modifier = modifier
            )
        }

    if (state.value.isLoading ){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = Color.Transparent.copy(alpha = 0.3f)
                ),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = Color.Transparent
                    ),
                color = Color.Red
            )
        }
    }

}

@Composable
fun BookListScreen(
    state: BookListState,
    onAction: (BookListScreenActions) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Books") }
                // No navigationIcon (no back button / listener)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.LightGray),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color.LightGray,
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BookList(
                        books = state.books,
                        onBookClick = { book ->
                            onAction(BookListScreenActions.OnBookClicked(book))
                        },
                        onScrollToPaginate = {
                            onAction(
                                BookListScreenActions.OnBookListScrolledToPaginate(
                                    state.page + 1
                                )
                            )
                        },
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}
