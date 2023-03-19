package kr.co.yeeun.lee.demoi.searchmovieapp.ui

interface OnMovieClickListener {
    fun onMovieItemClicked(url: String)
}

interface OnHistoryClickListener {
    fun onHistoryItemClicked(keyword: String)
}