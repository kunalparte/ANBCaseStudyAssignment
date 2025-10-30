package com.example.anbcasestudyassigment

sealed class Router(
    val route: String,
    val arguments: List<String> = emptyList()
) {
    fun createRoute(vararg args: Pair<String, Any?>): String {
        if (args.isEmpty()) return route
        val query = args.filter { it.second != null }
            .joinToString("&") { "${it.first}=${it.second}" }
        return "$route?$query"
    }

    object BookListScreen : Router("book_list")
    object BookDetailsScreen : Router(
        route = "book_details",
        arguments = listOf("bookId")
    )
}