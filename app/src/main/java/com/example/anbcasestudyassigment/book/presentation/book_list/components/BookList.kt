package com.example.anbcasestudyassigment.books.presentation.book_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.anbcasestudyassigment.book.presentation.book_list.components.BookListItem
import com.example.anbcasestudyassigment.books.domain.Book

@Composable
fun BookList(
    books: List<Book>,
    onBookClick: (book: Book) -> Unit,
    onScrollToPaginate : () -> Unit,
    modifier: Modifier = Modifier,
    scrollState: LazyListState = rememberLazyListState()
) {
    val shouldLoadMore = remember {
        derivedStateOf {
            val lastVisibleItem = scrollState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
            val totalItems = scrollState.layoutInfo.totalItemsCount
            // When user scrolls near end, trigger pagination
            lastVisibleItem != null && lastVisibleItem >= totalItems - 5
        }
    }

    // --- Trigger load more when threshold reached ---
    LaunchedEffect(shouldLoadMore.value) {
        if (shouldLoadMore.value) {
            onScrollToPaginate.invoke()
        }
    }
    LazyColumn(
        modifier = modifier,
        state = scrollState,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(
            items = books
        ) { book ->
            BookListItem(
                book = book,
                modifier = Modifier
                    .widthIn(max = 700.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                onClick = { onBookClick(book) }
            )
        }
    }
}
