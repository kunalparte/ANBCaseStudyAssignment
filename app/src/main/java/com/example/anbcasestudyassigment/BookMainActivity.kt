package com.example.anbcasestudyassigment

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.anbcasestudyassigment.book.presentation.book_detail.presentation.BookDetailsScreen
import com.example.anbcasestudyassigment.book.presentation.book_list.BookListScreenRoot
import com.example.anbcasestudyassigment.books.domain.Book
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookMainActivity : ComponentActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                Log.d("Kunal", "Called")
                AppNavigation()
            }
        }
    }

    @Composable
    fun AppNavigation() {
        val navController = rememberNavController()

        Scaffold { padding ->
            NavHost(
                navController = navController,
                startDestination = Router.BookListScreen.route,
                modifier = Modifier.padding(padding)
            ) {
                // --- Book List Screen (start) ---
                composable(Router.BookListScreen.route) {
                    BookListScreenRoot(
                        onBookClick = { book ->
                            // Put the Parcelable into the SavedStateHandle, then navigate
                            navController.currentBackStackEntry
                                ?.savedStateHandle
                                ?.set("book", book)

                            navController.navigate(Router.BookDetailsScreen.route)
                        }
                    )
                }

                // --- Book Details Screen (no route args) ---
                composable(Router.BookDetailsScreen.route) {
                    val book = navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.get<Book>("book")

                    // Guard against null (e.g., if process was killed)
                    if (book != null) {
                        BookDetailsScreen(
                            book = book,
                            onBack = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }



}