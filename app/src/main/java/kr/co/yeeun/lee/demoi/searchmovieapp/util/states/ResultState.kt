package kr.co.yeeun.lee.demoi.searchmovieapp.util.states

import androidx.annotation.StringRes

sealed class ResultState {
    object Success : ResultState() // 데이터 로드 성공
    object Loading : ResultState() // 데이터 로드 중
    class Failed(@StringRes val message: Int) : ResultState() // 데이터 로드 실패
}
