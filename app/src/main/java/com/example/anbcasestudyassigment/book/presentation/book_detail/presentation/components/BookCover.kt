package com.example.anbcasestudyassigment.book.presentation.book_detail.presentation.components

import android.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter

@Composable
fun BookCover(
    imageUrl: String,
    contentDescription: String
) {
    val painter = rememberAsyncImagePainter(model = imageUrl)
    val state = painter.state
    val isSuccess = state is AsyncImagePainter.State.Success
    val isLoading = state is AsyncImagePainter.State.Loading
    val isError = state is AsyncImagePainter.State.Error

    Column(
        modifier = Modifier
            .widthIn(max = 360.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.66f),
            contentAlignment = Alignment.Center
        ) {
            // Keep the image composed so Coil can drive state changes
            Image(
                painter = painter,
                contentDescription = contentDescription,
                modifier = Modifier
                    .matchParentSize()
                    .alpha(if (isSuccess) 1f else 0f), // hide until success
                contentScale = ContentScale.Crop
            )

            when {
                isLoading -> CircularProgressIndicator()
                isError -> Image(
                    painter = painterResource(R.drawable.ic_menu_report_image),
                    contentDescription = contentDescription,
                    modifier = Modifier.matchParentSize(),
                    contentScale = ContentScale.Fit
                )
            }
        }
    }
}
