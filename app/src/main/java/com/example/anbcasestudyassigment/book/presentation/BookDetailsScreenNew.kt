package com.example.anbcasestudyassigment.book.presentation

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.example.anbcasestudyassigment.books.domain.Book
import kotlin.math.roundToInt


@Preview
@Composable
fun BookDetailPreview(){
    val book = Book(
        id = "1",
        title = "KShatriya \n THe Rise Of Fighter",
        imageUrl = "",
        authors = listOf("Author1", "Author2", "Author3", "Author4","Author1", "Author2", "Author3", "Author4", "Author3", "Author4"),
        description = "A forgotten library hides stories that rewrite reality. When Mira opens a forbidden book, her own life starts to unravel into fiction.A forgotten library hides stories that rewrite reality. When Mira opens a forbidden book, her own life starts to unravel into fiction.",
        languages = listOf("Lang1", "Lang1","Lang1", "Lang1", "Lang1", "Lang1", "Lang1","Lang1", "Lang1", "Lang1"),
        firstPublishYear = "2025",
        averageRating = 4.5,
        ratingCount = 199,
        numPages = 100,
        numEditions = 10,
    )
    val state = remember { mutableStateOf(book) }
    Column( modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .background(
            color = Color.White
        )
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(color = Color.Black),
            contentAlignment = Alignment.BottomEnd
        ){
            BookCoverNew(url = state.value.imageUrl)
            BookRatingReview(
                averageRating = book.averageRating ?: 0.toDouble(),
                ratingCount = book.ratingCount ?: 0
            )

        }

        Column( modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(
                color = Color.White
            )
        ){
            BookTitle(title = book.title)

            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(8.dp))
            BookAuthorsT(authors = book.authors)

            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(8.dp))
            BookLanguages(langs = book.languages)

            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(8.dp))
            BookOverView( desc= book.description!!)
        }
    }
}

@Composable
fun BookDetailScreenRoot(
    book : Book
){
    val state = remember { mutableStateOf(book) }

}

@Composable
fun BookDetailScreenNew(
    book : Book
){

    Column(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .background(
            color = Color.White
        )
    ) {
        BookCoverNew(url = book.imageUrl)
        BookTitle(title = book.title)

    }
}

@Composable
fun BookCoverNew(
    url : String
) {

    val painter = rememberAsyncImagePainter(model = url)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .width(IntrinsicSize.Max)
            .height(250.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        Image(
            painter = painter,
            contentDescription = "cover_image",
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()

        )

        when (painter.state) {
            is AsyncImagePainter.State.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    color = ProgressIndicatorDefaults.circularDeterminateTrackColor,
                    strokeWidth = ProgressIndicatorDefaults.CircularStrokeWidth
                )
            }

            is AsyncImagePainter.State.Error -> {
                Text(
                    text = "Image Loadin Wrror...",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.titleMedium,
                    fontStyle = FontStyle.Italic,
                    fontFamily = FontFamily.Serif,
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                )
            }

            else -> {}
        }
    }
}


@Composable
fun BookRatingReview(
    averageRating : Double,
    ratingCount : Int
){
    Box(
        modifier = Modifier
            .wrapContentSize()
            .padding(16.dp)
    ){

        Column(modifier = Modifier
            .width(IntrinsicSize.Max)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(
                    size = 4.dp
                )
            )
            .shadow(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.Green),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "rating_icon",
                        tint = Color.Red,
                        modifier = Modifier
                            .size(24.dp)
                            .padding(start = 4.dp)
                    )
                    Text(
                        text = "${averageRating.roundToInt()}",
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center,
                        color = Color.Black,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(end = 4.dp)
                        )
                }

                Text(
                    text = "${ratingCount}\n Reviews",
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.White)
                    )
//            }
        }
    }
}

@Composable
fun BookTitle(
    title : String
){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = title,
            style = MaterialTheme.typography.headlineLarge,
            fontSize = 24.sp,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            letterSpacing = 0.3.sp,
            lineHeight = 32.sp,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(vertical = 12.dp)

        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BookAuthorsT(
    authors : List<String>
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 16.dp)
    ) {

        Text(
            text = "Authors",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
            letterSpacing = 0.2.sp,
            color = Color.Black,
            fontFamily = FontFamily.SansSerif,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(
                    vertical = 8.dp,
                    horizontal = 16.dp
                )
        )
        FlowRow(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 16.dp)
        ) {
            repeat(authors.size){ index ->
                Text(
                    text = authors[index],
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 4.dp, vertical = 4.dp)
                        .background(
                            color = Color.LightGray,
                            shape = RoundedCornerShape(
                                size = 8.dp
                            )
                        )
                        .border(
                            color = Color.Black,
                            width = 1.dp
                        )

                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BookLanguages(
    langs : List<String>
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 16.dp)
    ) {

        Text(
            text = "Langguages",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
            letterSpacing = 0.2.sp,
            color = Color.Black,
            fontFamily = FontFamily.SansSerif,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(
                    vertical = 8.dp,
                    horizontal = 16.dp
                )
        )
        FlowRow(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 16.dp)
        ) {
            repeat(langs.size){ index ->
                Text(
                    text = langs[index],
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 4.dp, vertical = 4.dp)
                        .background(
                            color = Color.LightGray,
                            shape = RoundedCornerShape(
                                size = 8.dp
                            )
                        )
                        .border(
                            color = Color.Black,
                            width = 1.dp
                        )

                )
            }
        }
    }
}

@Composable
fun BookOverView(
    desc : String
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ){

        Text(
            text = "OverView",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
            letterSpacing = 0.2.sp,
            color = Color.Black,
            fontFamily = FontFamily.SansSerif,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(
                    vertical = 8.dp,
                    horizontal = 16.dp
                )
        )

        Text(
            text = "$desc",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            fontSize = 14.sp,
            letterSpacing = 0.2.sp,
            color = Color.Black,
            fontFamily = FontFamily.Serif,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()

        )

    }
}