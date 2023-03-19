package kr.co.yeeun.lee.demoi.searchmovieapp.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kr.co.yeeun.lee.demoi.searchmovieapp.R
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewmodel: MovieViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}