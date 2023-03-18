package kr.co.yeeun.lee.demoi.searchmovieapp.data.model

data class MovieResponse(
    val total: Int = 0,
    val display: Int = 4,
    val items: List<Movie> = listOf()
)

data class Movie(
    val title: String = "",
    val link: String = "",
    val image: String = "",
    val pubDate: String = "",
    val userRating: String = ""
)