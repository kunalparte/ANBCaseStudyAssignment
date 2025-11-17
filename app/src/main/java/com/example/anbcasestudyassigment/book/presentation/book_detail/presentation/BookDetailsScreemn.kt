@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.anbcasestudyassigment.book.presentation.book_detail.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.anbcasestudyassigment.book.presentation.book_detail.presentation.components.BookCover
import com.example.anbcasestudyassigment.book.presentation.book_detail.presentation.components.LabeledValue
import com.example.anbcasestudyassigment.books.domain.Book
import com.example.anbcasestudyassigment.core.presentation.AppToolbar

@Composable
fun BookDetailsScreen(
    book: Book,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = book.title,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { padding ->
        BookDetailsContent(
            book = book,
            contentPadding = padding
        )
    }
}

@Composable
private fun BookDetailsContent(
    book: Book,
    contentPadding: PaddingValues
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .padding(contentPadding)
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Cover
        BookCover(
            imageUrl = book.imageUrl,
            contentDescription = book.title
        )

        Spacer(Modifier.height(16.dp))

        // Title
        Text(
            text = book.title,
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center
        )

        // Authors
        if (book.authors.isNotEmpty()) {
            Spacer(Modifier.height(8.dp))
            LabeledValue(
                label = "Author${if (book.authors.size > 1) "s" else ""}",
                value = book.authors.joinToString(", ")
            )
        }

        // Rating
        if (book.averageRating != null || book.ratingCount != null) {
            Spacer(Modifier.height(8.dp))
            RatingRow(
                average = book.averageRating,
                count = book.ratingCount
            )
        }

        Divider(Modifier.padding(vertical = 16.dp))

        // Description
        if (!book.description.isNullOrBlank()) {
            LabeledBlock(
                label = "Description",
                text = book.description!!
            )
            Spacer(Modifier.height(12.dp))
        }

        // Languages
        if (book.languages.isNotEmpty()) {
            LabeledValue(label = "Languages", value = book.languages.joinToString(", "))
            Spacer(Modifier.height(8.dp))
        }

        // First publish year
        if (!book.firstPublishYear.isNullOrBlank()) {
            LabeledValue(label = "First Published", value = book.firstPublishYear!!)
            Spacer(Modifier.height(8.dp))
        }

        // Pages
        book.numPages?.let {
            LabeledValue(label = "Pages", value = it.toString())
            Spacer(Modifier.height(8.dp))
        }

        // Editions
        LabeledValue(label = "Editions", value = book.numEditions.toString())

        Spacer(Modifier.height(24.dp))
    }
}

@Composable
fun RatingRow(average: Double?, count: Int?) {
    // TODO: Implement visual rating display
}

@Composable
fun LabeledBlock(label: String, text: String) {
    // TODO: Implement label + description block
}
