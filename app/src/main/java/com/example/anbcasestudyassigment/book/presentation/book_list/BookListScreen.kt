package com.example.anbcasestudyassigment.book.presentation.book_list

import androidx.compose.runtime.collectAsState


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.anbcasestudyassigment.books.domain.Book
import com.example.anbcasestudyassigment.books.presentation.book_list.components.BookList


@Preview(
    name = "Book List Screen Preview",
    showBackground = true,
    backgroundColor = 0xFFF5F5F5
)
@Composable
fun BookListScreenPreview() {


    // Fake state (normally from ViewModel)

    // Material theme wrapper (needed for preview)

}



@Composable
fun BookListScreenRoot(
    viewModel: BookListViewModel = hiltViewModel<BookListViewModel>(),
    onBookClick : (book : Book) -> Unit,
    modifier: Modifier = Modifier
){

    val state = viewModel.bookListState.collectAsState()

    BookListScreen(
        state = state.value,
        onAction = { action ->
            when(action){
                is BookListScreenActions.OnBookClicked -> {
                    onBookClick.invoke(action.book)
//                    viewModel.onAction(BookListScreenActions.OnBookClicked(action.book))
                }
                is BookListScreenActions.OnBookListScrolledToPaginate -> {
                    viewModel.getBookListData(mapOf(
                        "page" to action.pageCount,
                        "limit" to 20
                    ))
                }
                else -> Unit
            }

        }
    )
}

@Composable
fun BookListScreen(
    state: BookListState,
    onAction: (BookListScreenActions) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
            .statusBarsPadding(), // available in foundation for 1.1.x
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(),
            color = Color.LightGray,
            shape = RoundedCornerShape(
                topStart = 32.dp,
                topEnd = 32.dp
            ),
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
                        onAction(BookListScreenActions.OnBookListScrolledToPaginate(state.page + 1))
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}
