package com.example.anbcasestudyassigment.book.presentation.book_detail.presentation

import androidx.compose.runtime.Composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.style.TextAlign
import com.example.anbcasestudyassigment.book.presentation.book_detail.presentation.components.BookCover
import com.example.anbcasestudyassigment.book.presentation.book_detail.presentation.components.LabeledValue
import com.example.anbcasestudyassigment.books.domain.Book

// Your data class (as given)
/*
data class Book(
    val id: String,
    val title: String,
    val imageUrl: String,
    val authors: List<String>,
    val description: String?,
    val languages: List<String>,
    val firstPublishYear: String?,
    val averageRating: Double?,
    val ratingCount: Int?,
    val numPages: Int?,
    val numEditions: Int
)
*/

@Composable
fun BookDetailsScreen(
    book: Book,
    onBack: (() -> Unit)? = null
) {
    Scaffold(
        /*topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = book.title,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    if (onBack != null) {
                        IconButton(onClick = onBack) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                    }
                }
            )
        }*/
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
    val scroll = rememberScrollState()

    Column(
        modifier = Modifier
            .padding(contentPadding)
            .verticalScroll(scroll)
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Cover component
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

        // Rating (average + count)
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
    TODO("Not yet implemented")
}

@Composable
fun LabeledBlock(label: String, text: String) {
    TODO("Not yet implemented")
}


